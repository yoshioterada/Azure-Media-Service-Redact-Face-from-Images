# This is a sample application of Redact Face by usin Azure Media Services

The result of the reducted video image sample is as follows.  

Video : [Link of Demo Video](https://youtu.be/aAaIAlME3AY).  
[![](http://img.youtube.com/vi/aAaIAlME3AY/0.jpg)](https://youtu.be/aAaIAlME3AY)  


## 1. Create Media Service on Azure

At first, you need to create Azure Media Services on Azure environment.

### 1.1 Access to the Azure Portal
Please access to the [Azure Portal](http://portal.azure.com) ?
Then you can see following dash board.
![001](https://c1.staticflickr.com/5/4680/38890698762_52e01d2fe6_z.jpg)

### 1.2 Create the new Services
Please create the new resources? In the search field,please input ***Media Services*** ?

![002](https://c1.staticflickr.com/5/4572/38890699412_d3ff7789c3_z.jpg)

### 1.3 Create the Media Services
After you select the ***Media Services*** , you can see the following screen.

![003](https://c1.staticflickr.com/5/4647/38890699452_f1eb5ea1ba_z.jpg)

### 1.4 Fill in the input the field
You need to input the account name and new resource group name for this.
After that please select the subscription and storage account?
In this time, I will create new storage account.

![004](https://c1.staticflickr.com/5/4527/38890700122_dd422a364f_z.jpg)

### 1.5 Create new Storage account
If you would like to create the new storage account, please push the "New" button, then you can see the following screen.
Please input the name of the storage?

![005](https://c1.staticflickr.com/5/4555/38890699752_f80937bbb7_z.jpg)

### 1.6 Confirm all parameters and Create
After created the storage account, you can see the following screen. Please confirm again? If the paremeter is configured, please push the "Create" button?

![006](https://c1.staticflickr.com/5/4693/38890699942_12bf9dabcf_z.jpg)

### 1.7 Please wait a moment
Could you wait a moment until finished to create the Media Services.

![007](https://c1.staticflickr.com/5/4589/38890700762_2c901c8621_z.jpg)

### 1.8 Finished to create the Media Services
After for a while, you can see the following screeen after finished.
![008](https://c1.staticflickr.com/5/4586/38890700942_ea454cb8ff_z.jpg)

## 2. Setup and Configuration for the Application

### 2.1 Login to Azure by Azure CLI

In order to login to Azure, please execute ***az login*** command like follows?

```
$ az login
To sign in, use a web browser to open the page 
https://aka.ms/devicelogin 
and enter the code GCWXYE8PZ to authenticate.
```

Then you can see the URL and code on console, so please access to the [Device login Page](https://aka.ms/devicelogin) ?

Then you can see the following screen,please input the code to the field?

![009](https://c1.staticflickr.com/5/4635/38890701082_18415947c4_z.jpg)

After finished the login, you can see the following screen.

![010](https://c1.staticflickr.com/5/4528/38890701312_900ed46e57_z.jpg)

Please go back to the console? You can see the following result.

```
$ az login
To sign in, use a web browser to open the page https://aka.ms/devicelogin and enter the code GCWXYE8PZ to authenticate.
[
  {
    "cloudName": "AzureCloud",
    "id": "f77aafe8-****-****-****-d0c37687ef70",
    "isDefault": true,
    "name": "Microsoft Azure",
    "state": "Enabled",
    "tenantId": "72f988bf-****-****-****-2d7cd011db47",
    "user": {
      "name": "foobar@microsoft.com",
      "type": "user"
    }
  },
  {
    "cloudName": "AzureCloud",
    "id": "db56efb3-****-****-****-7841585fe607",
    "isDefault": false,
    "name": "Platform",
    "state": "Enabled",
    "tenantId": "72f988bf-****-****-****-2d7cd011db47",
    "user": {
      "name": "foobar@microsoft.com",
      "type": "user"
    }
  }
]
$ 

```

### 2.2 Create a Service Principal for Application

In order to create a Service Principal, please execute following command?
(Please change the password by yourself?)

```
$ az ad sp create-for-rbac --name Yoshio-Media-Service --password "J36JCd3hvF4tA"
Retrying role assignment creation: 1/36
{
  "appId": "171cf542-****-****-****-c39bab2ffeae",
  "displayName": "Yoshio-Media-Service",
  "name": "http://Yoshio-Media-Service",
  "password": "J36JCd3hvF4tA",
  "tenant": "72f988bf-****-****-****-2d7cd011db47"
}
```
### 2.3 Role Assignment

In order to assign the role, please execute "***az role assignment create***" command?  

```
$ az role assignment create \
--assignee "171cf542-****-****-****-c39bab2ffeae" \
--role Contributor --scope "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourcegroups/Azure-Media-Services/providers/microsoft.media/mediaservices/yoshio" 
```

In order to execute the command, you need to imput the scope.
For the scope value, you can get the it from the "Resource ID" from the Property.
![011](https://c1.staticflickr.com/5/4528/38890708682_b05896066f_z.jpg)

After executed it, you can see like following result.

```
$ az role assignment create --assignee "171cf542-****-****-****-c39bab2ffeae" --role Contributor --scope "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourcegroups/Azure-Media-Services/providers/microsoft.media/mediaservices/yoshio" 

{
  "id": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourcegroups/Azure-Media-Services/providers/microsoft.media/mediaservices/yoshio/providers/Microsoft.Authorization/roleAssignments/deee8381-****-****-****-810e974eb661",
  "name": "deee8381-****-****-****-810e974eb661",
  "properties": {
    "principalId": "65441707-****-****-****-295a67520bdf",
    "roleDefinitionId": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/providers/Microsoft.Authorization/roleDefinitions/b24988ac-****-****-****-20f7382dd24c",
    "scope": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourcegroups/Azure-Media-Services/providers/microsoft.media/mediaservices/yoshio"
  },
  "type": "Microsoft.Authorization/roleAssignments"
}
```

### 2.4 Confirm the Service Principal

After created and assigned the role, you can confirm it on screen. Please push the "API Access" Link? Then you can see the following screen. 
And please push the link of botom side?

![012](https://c1.staticflickr.com/5/4637/38890701522_c59094b5aa_z.jpg)

Then you can see the following screen.
In the "Azure AD Application" field,"Yoshio-Media-Service" which input on the "***az ad sp create-for-rbac***" command will be showed.

![013](https://c1.staticflickr.com/5/4541/38890701632_99a41515f2_z.jpg)

### 2.5 Confirm Storage Account

In the application, we need to access to both "Blob" and "Queue" service. In order to access them, we need to get the "Access Key".
Please change back the Screen to "Resouce Group" level?
Then you can see the "Azure Media Servicese" and "Storage Account" like follows.   
Then please push the storage account?

![014](https://c1.staticflickr.com/5/4569/38890701762_0b1c77f8d9_z.jpg)

Then you can see the following screen.

![015](https://c1.staticflickr.com/5/4684/38890701922_488afbc3cb_z.jpg)

On the screen, please push the ***"Access Key"***? Then following screen will be showed.  
Please copy the key to somethings?

![016](https://c1.staticflickr.com/5/4521/38890702322_2a916eff5d_z.jpg)

Then you need to confirm the endpoint for the Queue Services.
Please push the "Queue" and copy the Service endpoint URL?

![017](https://c1.staticflickr.com/5/4515/38890702592_63bc7a1b2b_z.jpg)

### 2.6 Application Configuration

After got the above access keys, you need to configure the Application configuration file as [app-resources.properties](https://github.com/yoshioterada/Azure-Media-Service-Redact-Face-from-Images/blob/master/src/main/resources/app-resources.properties) .

Please change the property value for your environment?
You can get the value for ***TENANT_ID, CLIENT_ID, CLIENT_KEY*** from the result of ***az ad sp create-for-rbac*** command.  

For ***QUEUE_ACCOUNT_NAME, QUEUE_ACCOUNT_KEY, QUEUE_URI***, please check again the 2.5 section?

```
TENANT_ID=72f988bf-****-****-****-2d7cd011db47
CLIENT_ID=171cf542-****-****-****-c39bab2ffeae
CLIENT_KEY=J36JCd3hvF4tA
REST_API_ENDPOINT=https://yoshio.restv2.japanwest.media.azure.net/api/

QUEUE_ACCOUNT_NAME=yoshioazuremedia
QUEUE_ACCOUNT_KEY=******************************************************************************+2dUoJnQ==
QUEUE_URI=https://yoshioazuremedia.queue.core.windows.net/

INPUT_DIRECTORY=/tmp/Media/in/
OUTPUT_DIRECTORY=/tmp/Media/out/
```

### 2.6 Build the Application

Please build the application?

```
$ mvn clean package

......
[INFO] Replacing original artifact with shaded artifact.
[INFO] Replacing /Users/tyoshio2002/NetBeansProjects/Azure-Media-Service/target/Azure-Media-Service-1.0-SNAPSHOT.jar with /Users/tyoshio2002/NetBeansProjects/Azure-Media-Service/target/Azure-Media-Service-1.0-SNAPSHOT-shaded.jar
[INFO] Dependency-reduced POM written at: /Users/tyoshio2002/NetBeansProjects/Azure-Media-Service/dependency-reduced-pom.xml
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 5.712 s
[INFO] Finished at: 2017-12-09T14:10:44+09:00
[INFO] Final Memory: 33M/315M
[INFO] ------------------------------------------------------------------------
```

After build the application, you can see the executable jar file on the target directory.

```
$ cd target
$ ls -l
total 21976
-rw-r--r--  1 yoterada  staff  11228056 12  9 14:10 Azure-Media-Service-1.0-SNAPSHOT.jar
drwxr-xr-x  5 yoterada  staff       160 12  9 14:10 classes
drwxr-xr-x  3 yoterada  staff        96 12  9 14:10 generated-sources
drwxr-xr-x  3 yoterada  staff        96 12  9 14:10 maven-archiver
drwxr-xr-x  3 yoterada  staff        96 12  9 14:10 maven-status
-rw-r--r--  1 yoterada  staff     19141 12  9 14:10 original-Azure-Media-Service-1.0-SNAPSHOT.jar
```

### 2.7 Copy the video image to input directory.

If you copy the image to ***INPUT_DIRECTORY*** and execute the application, you can see the redacted video image on ***OUTPUT_DIRECTORY***.

```
$ mkdir /tmp/Media
$ mkdir /tmp/Media/in/
$ mkdir /tmp/Media/out/
$ cp ***.mp4 /tmp/Media/in/
```

### 2.8 Execute the Application

Afte copy the image, please execute the command of ***java -jar Azure-Media-Service-1.0-SNAPSHOT.jar*** ?

```
$ java -jar Azure-Media-Service-1.0-SNAPSHOT.jar 
Picked up JAVA_TOOL_OPTIONS: -Dfile.encoding=UTF-8
Dec 09, 2017 2:29:16 PM com.yoshio3.RedactFaceService uploadFileAndCreateAsset
情報: Uploaded File /tmp/Media/in/cognitive.mp4
Dec 09, 2017 2:29:26 PM com.yoshio3.RedactFaceService checkFinishedOperationForRedact
情報: PROGRESS : redacted_face_cognitive.mp4
Dec 09, 2017 2:29:31 PM com.yoshio3.RedactFaceService checkFinishedOperationForRedact
情報: PROGRESS : redacted_face_cognitive.mp4
Dec 09, 2017 2:32:26 PM com.yoshio3.RedactFaceService checkFinishedOperationForRedact
情報: FINISHED : redacted_face_cognitive.mp4
Dec 09, 2017 2:32:28 PM com.yoshio3.RedactFaceService lambda$null$2
情報: DOWNLOAD File name : cognitive_redacted.mp4
Dec 09, 2017 2:33:25 PM com.yoshio3.RedactFaceService lambda$null$2
情報: DOWNLOAD File name : cognitive.mp4
```

Then you can see the redacted image on out directory.

```
$ ls -l /tmp/Media/out/
total 183616
-rw-r--r--  1 yoterada  wheel  94007905 12  9 14:33
cognitive_redacted.mp4
```