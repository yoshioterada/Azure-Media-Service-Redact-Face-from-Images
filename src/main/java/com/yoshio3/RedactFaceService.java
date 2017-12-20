/**
 *
 * @author Yoshio Terada
 *
 *         Copyright (c) 2017 Yoshio Terada
 *
 *         Permission is hereby granted, free of charge, to any person obtaining
 *         a copy of this software and associated documentation files (the
 *         "Software"), to deal in the Software without restriction, including
 *         without limitation the rights to use, copy, modify, merge, publish,
 *         distribute, sublicense, and/or sell copies of the Software, and to
 *         permit persons to whom the Software is furnished to do so, subject to
 *         the following conditions:
 *
 *         The above copyright notice and this permission notice shall be
 *         included in all copies or substantial portions of the Software.
 *
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *         EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *         MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *         NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 *         BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *         ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *         CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *         SOFTWARE.
 */
package com.yoshio3;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.ListBlobItem;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.queue.CloudQueueMessage;
import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.services.media.MediaConfiguration;
import com.microsoft.windowsazure.services.media.MediaContract;
import com.microsoft.windowsazure.services.media.MediaService;
import com.microsoft.windowsazure.services.media.WritableBlobContainerContract;
import com.microsoft.windowsazure.services.media.authentication.*;
import com.microsoft.windowsazure.services.media.models.*;
import com.microsoft.windowsazure.services.queue.QueueConfiguration;
import com.microsoft.windowsazure.services.queue.QueueContract;
import com.microsoft.windowsazure.services.queue.QueueService;
import com.yoshio3.storagequeue.QueueMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.EnumSet;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

/**
 *
 * @author Yoshio Terada
 */
public class RedactFaceService {

    private final static Logger LOGGER = Logger.getLogger(RedactFaceService.class.getName());

    private final static String FILE_PREFIX_NAME = "redacted_face_";
    private final static String FILE_SUFFIX_NAME = "_redacted";
    private final static String TENANT_ID;
    private final static String CLIENT_ID;
    private final static String CLIENT_KEY;
    private final static String REST_API_ENDPOINT;

    private final static String QUEUE_ACCOUNT_KEY;
    private final static String QUEUE_ACCOUNT_NAME;
    private final static String QUEUE_URI;
    private final static String MEDIA_QUEUE_NAME = "media-service-queue";

    private final static String OUTPUT_DIRECTORY;

    static {
        TENANT_ID = PropertyReader.getPropertyValue("TENANT_ID");
        CLIENT_ID = PropertyReader.getPropertyValue("CLIENT_ID");
        CLIENT_KEY = PropertyReader.getPropertyValue("CLIENT_KEY");
        REST_API_ENDPOINT = PropertyReader.getPropertyValue("REST_API_ENDPOINT");

        QUEUE_ACCOUNT_KEY = PropertyReader.getPropertyValue("QUEUE_ACCOUNT_KEY");
        QUEUE_ACCOUNT_NAME = PropertyReader.getPropertyValue("QUEUE_ACCOUNT_NAME");
        QUEUE_URI = PropertyReader.getPropertyValue("QUEUE_URI");

        OUTPUT_DIRECTORY = PropertyReader.getPropertyValue("OUTPUT_DIRECTORY");
    }
    private MediaContract mediaService;
    private ExecutorService executorService;
    private QueueContract queueService;
    private JobNotificationSubscription jobNotificationSubcription;
    private boolean checkInit = false;

    /**
     * This method initialize the ReadctFaceServices.
     * 
     * @throws MalformedURLException
     * @throws URISyntaxException
     * @throws ServiceException
     */
    public void init() throws MalformedURLException, URISyntaxException, ServiceException {
        executorService = Executors.newFixedThreadPool(1);
        try {
            //アクセス・トークンの取得
            AzureAdTokenCredentials credentials = new AzureAdTokenCredentials(
                    TENANT_ID,
                    new AzureAdClientSymmetricKey(CLIENT_ID, CLIENT_KEY),
                    AzureEnvironments.AZURE_CLOUD_ENVIRONMENT);
            TokenProvider provider = new AzureAdTokenProvider(credentials, executorService);
            Configuration configuration = MediaConfiguration.configureWithAzureAdTokenProvider(
                    new URI(REST_API_ENDPOINT),
                    provider);
            
            try {
                AzureAdAccessToken acquireAccessToken = provider.acquireAccessToken();
                String accessToken = acquireAccessToken.getAccessToken();
            } catch (Exception ex) {
                Logger.getLogger(RedactFaceService.class.getName()).log(Level.SEVERE, null, ex);
            }

            //メディア・サービス　インスタンス生成 
            mediaService = MediaService.create(configuration);

            Configuration queueConfig = Configuration.getInstance();
            queueConfig.setProperty(QueueConfiguration.ACCOUNT_KEY, QUEUE_ACCOUNT_KEY);
            queueConfig.setProperty(QueueConfiguration.ACCOUNT_NAME, QUEUE_ACCOUNT_NAME);
            queueConfig.setProperty(QueueConfiguration.URI, QUEUE_URI);
            //Queue サービス
            queueService = QueueService.create(queueConfig);

            queueService.createQueue(MEDIA_QUEUE_NAME);

            String notificationEndPointName = UUID.randomUUID().toString();
            mediaService.create(NotificationEndPoint.create(notificationEndPointName, EndPointType.AzureQueue, MEDIA_QUEUE_NAME));

            ListResult<NotificationEndPointInfo> listNotificationEndPointInfos = mediaService.list(NotificationEndPoint.list());
            String notificationEndPointId = null;

            for (NotificationEndPointInfo notificationEndPointInfo : listNotificationEndPointInfos) {
                if (notificationEndPointInfo.getName().equals(notificationEndPointName)) {
                    notificationEndPointId = notificationEndPointInfo.getId();
                }
            }
            jobNotificationSubcription = getJobNotificationSubscription(notificationEndPointId, TargetJobState.All);
            checkInit = true;
            LOGGER.log(Level.FINE, "Media Service Initialize finished.");
        } catch (MalformedURLException | URISyntaxException ex) {
            throw ex;
        }
    }

    /**
     * Destroy method.
     * Please invoke after the operation.
     *
     */
    public void desroy() {
        if (executorService != null) {
            executorService.shutdown();
        }
        LOGGER.log(Level.FINE, "Shutdown Completed.");
    }

    /**
     * List all of exsitinc Assets.
     * 
     * @return ListResult of AssetInfo
     * @throws ServiceException
     */
    public ListResult<AssetInfo> listAssetID() throws ServiceException {
        if(!checkInit){
            throw new ServiceException("Please invoke init() method before call this method.");
        }
        // アセットの一覧取得
        ListResult<AssetInfo> assets = mediaService.list(Asset.list());
        assets.forEach(asset -> LOGGER.log(Level.FINE, "{0} : {1}", new String[]{asset.getName(), asset.getId()}));
        return assets;
    }

    /**
     * Delete specified Asset
     * @param asset
     * @throws ServiceException
     */
    public void deleteAsset(AssetInfo asset) throws ServiceException {
        if(!checkInit){
            throw new ServiceException("Please invoke init() method before call this method.");
        }
        try {
            mediaService.delete(Asset.delete(asset.getId()));
        } catch (ServiceException ex) {
            Logger.getLogger(RedactFaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete all of the Assets
     * 
     * @throws ServiceException
     */
    public void deleteAllAssets() throws ServiceException {
        if(!checkInit){
            throw new ServiceException("Please invoke init() method before call this method.");
        }
        ListResult<AssetInfo> assets = mediaService.list(Asset.list());
        assets.iterator().forEachRemaining((AssetInfo asset) -> {
            try {
                mediaService.delete(Asset.delete(asset.getId()));
            } catch (ServiceException ex) {
                Logger.getLogger(RedactFaceService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Redact(Masking) the Face of Assets
     * 
     * @param assetInfo
     * @return JobInfo
     * @throws ServiceException
     * @throws Exception
     */
    public JobInfo redactFaceFromVideo(AssetInfo assetInfo) throws ServiceException, Exception {
        if(!checkInit){
            throw new ServiceException("Please invoke init() method before call this method.");
        }
        String assetId = assetInfo.getId();
        String assetName = assetInfo.getName();
        
        //This file is already masked
        if(assetName.contains(FILE_PREFIX_NAME)){
            return null;
        }

        JobInfo job;
        //MediaProcessor 情報の取得
        ListResult<MediaProcessorInfo> mediaProcessors = mediaService.list(MediaProcessor.list());
        for (MediaProcessorInfo mpi : mediaProcessors) {
            if (mpi.getName().equals("Azure Media Redactor")) {
                String id = mpi.getId();
                String outputFileName = FILE_PREFIX_NAME + assetName;

                String taskBody = "<?xml version=\"1.0\" encoding=\"utf-8\"?><taskBody><inputAsset>JobInputAsset(0)</inputAsset><outputAsset assetCreationOptions=\"0\" assetName=\"" + outputFileName + "\">JobOutputAsset(0)</outputAsset></taskBody>";

                Task.CreateBatchOperation task = Task.create(id, taskBody);
                task.setName("Azure Media Redactor for " + outputFileName)
                        .setConfiguration("{'version':'1.0', 'options': {'Mode': 'Combined', 'BlurType': 'High'}}");

                Job.Creator jobCreator = Job.create()
                        .setName(outputFileName)
                        .addInputMediaAsset(assetId)
                        .setPriority(0)
                        .addJobNotificationSubscription(jobNotificationSubcription)
                        .addTaskCreator(task);

                job = mediaService.create(jobCreator);
                String jobId = job.getId();
                LOGGER.log(Level.FINE, "Created Job for {0} as JobId: {1}", new Object[]{assetName, jobId});

                return job;
            }
        }
        return null;
    }

    /**
     * Publish the redacted asset to publicly 
     *
     * @param job : JobInfo of finished job
     * @throws ServiceException
     * @throws IOException
     */
    public void publishContents(JobInfo job) throws ServiceException, IOException {
        if(!checkInit){
            throw new ServiceException("Please invoke init() method before call this method.");
        }
        ListResult<AssetInfo> outputAssets = mediaService.list(Asset.list(job.getOutputAssetsLink()));
        AssetInfo assetInfo = outputAssets.get(0);

        // Create an access policy that provides Read access for 15 minutes.
        AccessPolicyInfo downloadAccessPolicy = mediaService.create(AccessPolicy.create("Download", 15.0, EnumSet.of(AccessPolicyPermission.READ)));

        // Create a locator using the access policy and asset.
        // This will provide the location information needed to access the asset.
        LocatorInfo locatorInfo = mediaService.create(Locator.create(downloadAccessPolicy.getId(), assetInfo.getId(), LocatorType.SAS));

        // Iterate through the files associated with the asset.
        for (AssetFileInfo assetFile : mediaService.list(AssetFile.list(assetInfo.getAssetFilesLink()))) {
            String file = assetFile.getName();
            String locatorPath = locatorInfo.getPath();
            int startOfSas = locatorPath.indexOf("?");
            String blobPath = locatorPath + file;
            if (startOfSas >= 0) {
                blobPath = locatorPath.substring(0, startOfSas) + "/" + file + locatorPath.substring(startOfSas);
            }
            LOGGER.log(Level.FINE, "Path to asset file: {0}", blobPath);
        }
    }

    /**
     * Confirm the status of the redacte operation.
     * 
     * In the redact operation, the status will be push to the Azure Storage
     * Queue services.
     * In this method, acceess to the queue and check the status.
     * 
     * Note: 
     * In this method, operation success or failed with some error,
     * it will return true as finished.
     * Only progress, it will return false.
     *
     * @param job
     * @return true : finished, false : not finished
     * @throws ServiceException
     * @throws URISyntaxException
     * @throws StorageException
     * @throws InvalidKeyException
     */
    public boolean checkFinishedOperationForRedact(JobInfo job) throws ServiceException, URISyntaxException, StorageException, InvalidKeyException {
        if(!checkInit){
            throw new ServiceException("Please invoke init() method before call this method.");
        }
        boolean finished;

        String storageConnectionString = getStorageConnectionString();

        CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
        CloudQueueClient queueClient = storageAccount.createCloudQueueClient();
        CloudQueue queue = queueClient.getQueueReference(MEDIA_QUEUE_NAME);
        CloudQueueMessage retrieveMessage = queue.retrieveMessage();
        if (retrieveMessage == null) {
            return false;
        }
        String message = retrieveMessage.getMessageContentAsString();
        LOGGER.log(Level.FINE, message);

        JsonbConfig config = new JsonbConfig().withNullValues(true);
        Jsonb jsonb = JsonbBuilder.create(config);
        QueueMessage queueMessage = jsonb.fromJson(message, QueueMessage.class);

        String newState = queueMessage.getProperties().getNewState();
        if (newState != null) {
            switch (newState) {
                case "Finished":
                    LOGGER.log(Level.INFO, "FINISHED : {0}", queueMessage.getProperties().getJobName());
                    finished = true;
                    break;
                case "Error":
                    LOGGER.log(Level.INFO, "ERROR : {0}", queueMessage.getProperties().getJobName());
                    LOGGER.log(Level.FINE, "FINISHED : {0}", queueMessage.toString());
                    finished = true;
                    break;
                default:
                    LOGGER.log(Level.INFO, "PROGRESS : {0}", queueMessage.getProperties().getJobName());
                    finished = false;
                    break;
            }
        } else {
            finished = false;
        }
        queue.deleteMessage(retrieveMessage);
        return finished;
    }

    /**
     * Upload and create Asset
     * 
     * @param assetName
     * @param fileName
     * @return
     * @throws ServiceException
     * @throws FileNotFoundException
     * @throws NoSuchAlgorithmException
     */
    public AssetInfo uploadFileAndCreateAsset(String assetName, String fileName)
            throws ServiceException, FileNotFoundException, NoSuchAlgorithmException {
        if(!checkInit){
            throw new ServiceException("Please invoke init() method before call this method.");
        }
        WritableBlobContainerContract uploader;
        AssetInfo resultAsset;
        AccessPolicyInfo uploadAccessPolicy;
        LocatorInfo uploadLocator = null;

        // Create an Asset
        resultAsset = mediaService.create(Asset.create().setName(assetName).setAlternateId("altId"));
        LOGGER.log(Level.FINE, "Created Asset {0}", fileName);

        // Create an AccessPolicy that provides Write access for 15 minutes
        uploadAccessPolicy = mediaService
                .create(AccessPolicy.create("uploadAccessPolicy", 15.0, EnumSet.of(AccessPolicyPermission.WRITE)));

        // Create a Locator using the AccessPolicy and Asset
        uploadLocator = mediaService
                .create(Locator.create(uploadAccessPolicy.getId(), resultAsset.getId(), LocatorType.SAS));

        // Create the Blob Writer using the Locator
        uploader = mediaService.createBlobWriter(uploadLocator);

        File file = new File(fileName);

        // The local file that will be uploaded to your Media Services account
        InputStream input = new FileInputStream(file);

        LOGGER.log(Level.FINE, "Uploading {0}", fileName);

        // Upload the local file to the media asset
        uploader.createBlockBlob(file.getName(), input);

        // Inform Media Services about the uploaded files
        mediaService.action(AssetFile.createFileInfos(resultAsset.getId()));
        LOGGER.log(Level.INFO, "Uploaded File {0}", fileName);

        mediaService.delete(Locator.delete(uploadLocator.getId()));
        mediaService.delete(AccessPolicy.delete(uploadAccessPolicy.getId()));

        return resultAsset;
    }

    /**
     * Download allof assets files.
     *
     * @param assets
     * @throws URISyntaxException
     * @throws InvalidKeyException
     * @throws ServiceException
     */
    public void downLoadRedactedImageFile(ListResult<AssetInfo> assets) throws URISyntaxException, InvalidKeyException, ServiceException {
        if(!checkInit){
            throw new ServiceException("Please invoke init() method before call this method.");
        }
        String storageConnectionString = getStorageConnectionString();
        // Retrieve storage account from connection-string.
        CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

        // Create the blob client.
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

        assets.listIterator().forEachRemaining((AssetInfo assetInfo) -> {
            try {
                String getUri = assetInfo.getUri();
                URI uri = new URI(getUri);
                String path = uri.getPath();
                String container = path.replaceFirst("/", "");

                CloudBlobContainer blobContainer = blobClient.getContainerReference(container);

                blobContainer.listBlobs().forEach((ListBlobItem blobItem) -> {
                    if (blobItem instanceof CloudBlob) {
                        // Download the item and save it to a file with the same name.
                        CloudBlob blob = (CloudBlob) blobItem;
                        String fileName = blob.getName();
                        LOGGER.log(Level.INFO, "DOWNLOAD File name : {0}", fileName);

                        if (fileName.contains(FILE_SUFFIX_NAME)) {
                            try {
                                blob.download(new FileOutputStream(OUTPUT_DIRECTORY + fileName));
                            } catch (FileNotFoundException | StorageException ex) {
                                LOGGER.log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
            } catch (URISyntaxException | StorageException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    private String getStorageConnectionString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DefaultEndpointsProtocol=https;")
                .append("AccountName=")
                .append(QUEUE_ACCOUNT_NAME)
                .append(";AccountKey=")
                .append(QUEUE_ACCOUNT_KEY)
                .append(";EndpointSuffix=core.windows.net");
        return builder.toString();
    }
    
    private JobNotificationSubscription getJobNotificationSubscription(String jobNotificationSubscriptionId,
            TargetJobState targetJobState) {
        return new JobNotificationSubscription(jobNotificationSubscriptionId, targetJobState);
    }    
}
