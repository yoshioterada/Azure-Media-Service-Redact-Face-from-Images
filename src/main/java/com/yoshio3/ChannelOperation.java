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

import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.services.media.MediaConfiguration;
import com.microsoft.windowsazure.services.media.authentication.AzureAdAccessToken;
import com.microsoft.windowsazure.services.media.authentication.AzureAdClientSymmetricKey;
import com.microsoft.windowsazure.services.media.authentication.AzureAdTokenCredentials;
import com.microsoft.windowsazure.services.media.authentication.AzureAdTokenProvider;
import com.microsoft.windowsazure.services.media.authentication.AzureEnvironments;
import com.microsoft.windowsazure.services.media.authentication.TokenProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.yoshio3.json.AccessControl;
import com.yoshio3.json.Allow;
import com.yoshio3.json.ChannelInput;
import com.yoshio3.json.Input;
import com.yoshio3.json.Ip;
import com.yoshio3.json.ListChannel;
import com.yoshio3.json.ChannelValue;
import com.yoshio3.json.JobOperationStatus;
import com.yoshio3.json.ListPrograms;
import com.yoshio3.json.Preview;
import com.yoshio3.json.ProgramValue;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yoshio Terada
 */
public class ChannelOperation {

    private final static Logger LOGGER = Logger.getLogger(ChannelOperation.class.getName());

    private final static String TENANT_ID;
    private final static String CLIENT_ID;
    private final static String CLIENT_KEY;
    private final static String REST_API_ENDPOINT;
    private final static String ENDPOINT_HOST_NAME;

    private String accessToken;

    static {
        TENANT_ID = PropertyReader.getPropertyValue("TENANT_ID");
        CLIENT_ID = PropertyReader.getPropertyValue("CLIENT_ID");
        CLIENT_KEY = PropertyReader.getPropertyValue("CLIENT_KEY");
        REST_API_ENDPOINT = PropertyReader.getPropertyValue("REST_API_ENDPOINT");
        URL url = null;
        try {
            url = new URL(REST_API_ENDPOINT);
        } catch (MalformedURLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        ENDPOINT_HOST_NAME = url.getHost();
    }

    /**
     * 初期化処理
     *
     * @throws MalformedURLException
     * @throws URISyntaxException
     * @throws ServiceException
     */
    public void init() throws MalformedURLException, URISyntaxException, ServiceException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
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

            AzureAdAccessToken acquireAccessToken = provider.acquireAccessToken();
            accessToken = acquireAccessToken.getAccessToken();
            LOGGER.log(Level.FINE, "ACCESS TOKEN {0}", accessToken);
        } catch (MalformedURLException | URISyntaxException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        executorService.shutdown();
    }

    /**
     * 指定したオペレーションの終了を待機
     *
     * @param operationId : 監視したいオペレーションのID
     * @return channelID : 作業が完了したチャンネル ID
     * @throws InvalidChannelOperationException
     */
    public String isCompleted(String operationId) throws InvalidChannelOperationException {

        boolean finishFlag = false;
        while (!finishFlag) {
            ClientResponse response = requestRESTEndpoint("Operations('" + operationId + "')")
                    .get(ClientResponse.class);

            Response.StatusType responseStatus = response.getStatusInfo();
            if (responseStatus.getStatusCode() != 200) {
                throw new InvalidChannelOperationException("isCompleted() failed :" + responseStatus.getStatusCode() + " : " + responseStatus.getReasonPhrase());
            }

            String returnValue = response.getEntity(String.class);
            JsonbConfig config = new JsonbConfig();
            config.withNullValues(Boolean.TRUE);
            Jsonb jsonb = JsonbBuilder.create(config);
            JobOperationStatus jobStatus = jsonb.fromJson(returnValue, JobOperationStatus.class);
            LOGGER.log(Level.FINE, "isCompleted() status : {0}", returnValue);

            switch (jobStatus.getState()) {
                case "InProgress":
                    LOGGER.log(Level.INFO, "InProgress .....");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    break;
                case "Succeeded":
                    LOGGER.log(Level.INFO, "Succeeded");
                    return jobStatus.getTargetEntityId();
                case "Failed":
                    LOGGER.log(Level.SEVERE, "Failed");
                    throw new InvalidChannelOperationException(jobStatus.getErrorCode() + " : " + jobStatus.getErrorMessage());
                default:
                    break;
            }
        }
        return "";
    }

    /**
     * チャンネル名からチャンネル ID の取得
     *
     * @param name : チャンネル名
     * @return チャンネル ID
     */
    public Optional<String> getChannelIDFromName(String name) {
        List<ChannelValue> listChannel = listChannel();
        return listChannel.stream()
                .filter((ChannelValue channelValue) -> channelValue.getName().equals(name))
                .map((ChannelValue channelValue) -> channelValue.getId())
                .findFirst();
    }

    /**
     * チャンネルの一覧を取得
     *
     * @return ChannelValue のリスト
     */
    public List<ChannelValue> listChannel() {
        String getResult = requestRESTEndpoint("Channels")
                .get(String.class);
        JsonbConfig config = new JsonbConfig();
        config.withNullValues(Boolean.TRUE);
        Jsonb jsonb = JsonbBuilder.create(config);

        ListChannel fromJson = jsonb.fromJson(getResult, ListChannel.class);
        LOGGER.log(Level.FINE, "LIST CHANNEL: {0}", fromJson);

        List<ChannelValue> result = fromJson.getValue();
        return result;
    }

    /**
     * 指定したチャンネル ID の開始
     *
     * @param channelID：チャンネル ID からチャンネルの開始
     * @return operation-id：操作を監視するための操作 ID
     * @throws InvalidChannelOperationException コード例      <pre>
     * {@code
     * ChannelOperation channelOperation = new ChannelOperation();
     * channelOperation.init();
     *
     * Optional<String> channelID =
     * channelOperation.getChannelIDFromName("TEST4");
     * channelID.ifPresent(id -> {
     *     Optional<String> startChannel = channelOperation.startChannel(id);
     *     startChannel.ifPresent(opId -> { System.out.println(opId); String
     *         suscceededChannelID = channelOperation.isCompleted(opId);
     *     });
     * });
     * }
     * </pre>
     *
     */
    public Optional<String> startChannel(String channelID) throws InvalidChannelOperationException {
        //https://media.windows.net/api/Channels(‘*channelid*’)/Start       
        //https://stackoverflow.com/questions/45372982/how-to-disable-chunked-encoding-and-use-buffered-for-jersey-1
        ClientResponse response = requestRESTEndpoint("Channels('" + channelID + "')/Start")
                .post(ClientResponse.class, "{}");

        Response.StatusType responseStatus = response.getStatusInfo();
        if (responseStatus.getStatusCode() == 202) {
            return getOperationId(response);
        } else {
            throw new InvalidChannelOperationException("start channel failed :" + responseStatus.getStatusCode() + " : " + responseStatus.getReasonPhrase());
        }
    }

    /**
     * 指定したチャンネル ID の停止
     *
     * @param channelID：チャンネル ID からチャンネルの停止
     * @return operation-id：操作を監視するための操作 ID
     * @throws InvalidChannelOperationException コード例      <pre>
     * {@code
     *       ChannelOperation channelOperation = new ChannelOperation();
     *       channelOperation.init();
     *       Optional<String> channelID = channelOperation.getChannelIDFromName("TEST4");
     *       channelID.ifPresent(id -> {
     *           Optional<String> stopChannel = channelOperation.stopChannel(id);
     *           stopChannel.ifPresent(opId -> {
     *                System.out.println(opId);
     *               String suscceededChannelID = channelOperation.isCompleted(opId);
     *           });
     *       });
     *
     * }
     * </pre>
     *
     */
    public Optional<String> stopChannel(String channelID) throws InvalidChannelOperationException {
        ClientResponse response = requestRESTEndpoint("Channels('" + channelID + "')/Stop")
                .post(ClientResponse.class, "{}");
        LOGGER.fine(response.getStatusInfo().getReasonPhrase());

        Response.StatusType responseStatus = response.getStatusInfo();
        if (responseStatus.getStatusCode() == 202) {
            return getOperationId(response);
        } else {
            throw new InvalidChannelOperationException("stop channel failed :" + responseStatus.getStatusCode() + " : " + responseStatus.getReasonPhrase());
        }
    }

    /**
     * 指定したチャンネル ID のリセット
     *
     * @param channelID：チャンネル ID からチャンネルをリセット
     * @return operation-id：操作を監視するための操作 ID
     * @throws InvalidChannelOperationException
     *
     * コード例      <pre>
     * {@code
     *       ChannelOperation channelOperation = new ChannelOperation();
     *       channelOperation.init();
     *       Optional<String> channelID = channelOperation.getChannelIDFromName("TEST4");
     *       channelID.ifPresent(id -> {
     *           Optional<String> stopChannel = channelOperation.stopChannel(id);
     *           stopChannel.ifPresent(opId -> {
     *                System.out.println(opId);
     *               String suscceededChannelID = channelOperation.isCompleted(opId);
     *           });
     *       });
     *
     * }
     * </pre>
     *
     */
    public Optional<String> resetChannel(String channelID) throws InvalidChannelOperationException {
        ClientResponse response = requestRESTEndpoint("Channels('" + channelID + "')/Reset")
                .post(ClientResponse.class, "{}");
        LOGGER.fine(response.getStatusInfo().getReasonPhrase());

        Response.StatusType responseStatus = response.getStatusInfo();
        if (responseStatus.getStatusCode() == 202) {
            return getOperationId(response);
        } else {
            throw new InvalidChannelOperationException("reset channel failed :" + responseStatus.getStatusCode() + " : " + responseStatus.getReasonPhrase());
        }
    }

    /**
     * 新規チャンネルの作成
     *
     * 注意： ご利用の環境に応じては制限により 5 つ以上のチャンネルを作成できない場合があります。
     * 作成可能なチャンネル数の上限を増やしたい場合は、サポートへご連絡ください。
     *
     * @param name：チャンネル名
     * @param protocol：プロトコル : RTMP
     * @param sourceIP : IP アドレスのリスト
     * @param previewIP : プレビュー IP アドレスのリスト
     * @return operation-id：操作を監視するための操作 ID
     * @throws ExecutionException
     * @throws InterruptedException
     *
     * コード例      <pre>
     * {@code
     *       ChannelOperation channelOperation = new ChannelOperation();
     *       channelOperation.init();
     *       Optional<String> createChannel = channelOperation.createChannel("TEST6", "RTMP", channelOperation.createTemporalIPList(), channelOperation.createTemporalIPList());
     *       createChannel.ifPresent(opId -> {
     *           System.out.println(opId);
     *           String suscceededChannelID = channelOperation.isCompleted(opId);
     *       });
     * }
     * </pre>
     *
     */
    public Optional<String> createChannel(String name, String protocol, List<String> sourceIP, List<String> previewIP) throws ExecutionException, InterruptedException, InvalidChannelOperationException {
        ChannelInput createChannelInput = createChannelInput(name, protocol, sourceIP, previewIP);

        JsonbConfig config = new JsonbConfig();
        config.withNullValues(Boolean.TRUE);
        Jsonb jsonb = JsonbBuilder.create(config);
        String json = jsonb.toJson(createChannelInput);
        LOGGER.log(Level.FINE, json);

        ClientResponse response = requestRESTEndpoint("Channels")
                .post(ClientResponse.class, json);
        LOGGER.log(Level.FINE, "{0} : {1}", new Object[]{response.getStatusInfo().getStatusCode(), response.getStatusInfo().getReasonPhrase()});
        Response.StatusType responseStatus = response.getStatusInfo();

        if (responseStatus.getStatusCode() == 202) {
            return getOperationId(response);
        } else {
            throw new InvalidChannelOperationException("create channel failed :" + responseStatus.getStatusCode() + " : " + responseStatus.getReasonPhrase());
        }
    }

    //TODO
    /**
     * チャンネルの削除
     *
     * 使用例： String channelID = channelOperation.getChannelIDFromName("TEST4");
     * channelOperation.deleteChannel(channelID);
     *
     * @param channelID
     * @return
     * @throws com.yoshio3.InvalidChannelOperationException
     */
    public Optional<String> deleteChannel(String channelID) throws InvalidChannelOperationException {
        //指定したチャンネル ID 配下に存在するプログラム一覧を取得
        List<ProgramValue> listPrograms = listPrograms(channelID);
        listPrograms.forEach(programValue -> {
            try {
                deleteProgram(programValue.getId());
            } catch (InvalidChannelOperationException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        ClientResponse response = requestRESTEndpoint("Channels('" + channelID + "')")
                .delete(ClientResponse.class, "{}");

        LOGGER.log(Level.FINE, "{0} : {1}", new Object[]{response.getStatusInfo().getStatusCode(), response.getStatusInfo().getReasonPhrase()});
        Response.StatusType responseStatus = response.getStatusInfo();

        if (responseStatus.getStatusCode() == 202) {
            return getOperationId(response);
        } else {
            throw new InvalidChannelOperationException("delete channel failed :" + responseStatus.getStatusCode() + " : " + responseStatus.getReasonPhrase());
        }
    }

    private void deleteProgram(String programID) throws InvalidChannelOperationException {
        ClientResponse response = requestRESTEndpoint("Programs('" + programID + "')")
                .delete(ClientResponse.class, "{}");
        LOGGER.log(Level.FINE, "{0} : {1}", new Object[]{response.getStatusInfo().getStatusCode(), response.getStatusInfo().getReasonPhrase()});
        Response.StatusType responseStatus = response.getStatusInfo();

        if (responseStatus.getStatusCode() != 204) {
            throw new InvalidChannelOperationException("delete channel failed :" + responseStatus.getStatusCode() + " : " + responseStatus.getReasonPhrase());
        }
    }

    /**
     * 指定したチャンネル ID 内に含まれるプログラム一覧を取得
     *
     * @param channelID
     * @return ProgramValue のリスト
     */
    public List<ProgramValue> listPrograms(String channelID) {
        ClientResponse response = requestRESTEndpoint("Channels('" + channelID + "')/Programs").get(ClientResponse.class);
        String returnValue = response.getEntity(String.class);

        JsonbConfig config = new JsonbConfig();
        config.withNullValues(Boolean.TRUE);
        Jsonb jsonb = JsonbBuilder.create(config);
        ListPrograms listPrograms = jsonb.fromJson(returnValue, ListPrograms.class);

        return listPrograms.getValue();
    }

    /**
     * テンポラリの IP リストを生成 IP Addresss "0.0.0.0" のみのリストを生成
     *
     * @return String
     */
    public List<String> createTemporalIPList() {
        List<String> temporalIPList = new ArrayList<>();
        temporalIPList.add("0.0.0.0");
        return temporalIPList;
    }

    private WebResource.Builder requestRESTEndpoint(String operation) {
        Client client = Client.create();
        return client.resource(REST_API_ENDPOINT + operation)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.ACCEPT, "application/json")
                .header(HttpHeaders.ACCEPT_CHARSET, "UTF-8")
                .header(HttpHeaders.HOST, ENDPOINT_HOST_NAME)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header(HttpHeaders.CONTENT_LENGTH, 0)
                .header("DataServiceVersion", "3.0;NetFx")
                .header("MaxDataServiceVersion", "3.0;NetFx")
                .header("x-ms-version", "2.19");
    }

    private ChannelInput createChannelInput(String name, String protocol, List<String> liveSourceIpAddress, List<String> previewIpAddress) {
        ChannelInput channelInput = new ChannelInput();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.JAPAN);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        channelInput.setCreated(sdf.format(new Date()));
        channelInput.setId(null);
        channelInput.setName(name);
        channelInput.setEncodingType("None");
        channelInput.setDescription("");
        //ライブソース IP アドレスの指定
        Input createInput = createInput(name, protocol, liveSourceIpAddress);
        channelInput.setInput(createInput);
        //プレビューソース IP アドレスの指定
        Preview createPreview = createPreview(name, previewIpAddress);
        channelInput.setPreview(createPreview);
        return channelInput;
    }

    private Input createInput(String name, String protocol, List<String> liveSourceIpAddress) {
        AccessControl accessControl = createAccessControl(name, liveSourceIpAddress);
        Input input = new Input();
        input.setStreamingProtocol(protocol);
        input.setAccessControl(accessControl);
        return input;
    }

    private Preview createPreview(String name, List<String> previewIpAddress) {
        AccessControl accessControl = createAccessControl(name, previewIpAddress);

        Preview preview = new Preview();
        preview.setAccessControl(accessControl);
        preview.setEndpoints(null);

        return preview;
    }

    private AccessControl createAccessControl(String name, List<String> restrictedIpAddress) {
        List<Allow> allowList = new ArrayList<>();

        restrictedIpAddress.stream().forEach(ipAddress -> {
            Allow allow = new Allow();
            allow.setAddress(ipAddress);
            allow.setName(name);
            allow.setSubnetPrefixLength(24);  //24 bit mask
            allowList.add(allow);
        });
        Ip ip = new Ip();
        ip.setAllow(allowList);
        // ライブ IP アドレス制限
        AccessControl accessControl = new AccessControl();
        accessControl.setIp(ip);
        return accessControl;
    }

    private Optional<String> getOperationId(ClientResponse response) {
        // 成功した時
        MultivaluedMap<String, String> headers = response.getHeaders();
        Optional<String> operationId = headers.keySet()
                .stream()
                .filter((String key) -> key.equals("operation-id"))
                .map((String key) -> headers.get(key).get(0))
                .findFirst();
        return operationId;

    }
}
