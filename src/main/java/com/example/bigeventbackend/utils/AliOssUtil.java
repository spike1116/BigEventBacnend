package com.example.bigeventbackend.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.FileInputStream;
import java.io.InputStream;

public class AliOssUtil {

    private static final String ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";

    private static final String ACCESS_KEY_ID = "YOUR_KEY_ID";

    private static final String ACCESS_KEY_SECRET = "YOUR_KEY_SECRET";

    private static final String BUCKET_NAME = "spikebucket";

    public static String uploadFile(String objectName, InputStream fileInputStream) throws Exception {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        //EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        // 填写Bucket名称，例如examplebucket。Bucket名称在OSS范围内必须全局唯一。

//        创建OSSClient实例。
//        填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
//        String objectName = "img/001.png";
//        填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
//        如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
//        String filePath= "/Users/wanglei/Projects/SpringBootProjects/File/63887e9bd839ae7e3a286cdf8a438f73.JPG";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID,ACCESS_KEY_SECRET);
        String url = "";
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, objectName, fileInputStream);
            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            String regionalNode = ENDPOINT.substring(ENDPOINT.lastIndexOf("/")+1);
            url = "https://"+BUCKET_NAME+"."+regionalNode+"/"+objectName;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
            return url;
        }
    }

}
