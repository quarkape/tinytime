package club.hue.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class COSUploadUtil {

    // 初始化用户身份信息（secretId, secretKey）。
    String secretId = "AKID4xaw2rPggPq0uXtzoNfgQt5zcz4sPfeL";
    String secretKey = "RbW9VbR0II1UgbHhrkLgkLfiUyCAsIRJ";
    String bucketRegion = "ap-chengdu";
    String bucketName = "test-1300070782";
    String basicPath = "tinytime/";

    public boolean upLoadFile2COS(Long fileSize, String filename, MultipartFile file, String userid) throws IOException {
        // 创建cos客户端
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(bucketRegion);
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);

        InputStream inputStream = new BufferedInputStream(file.getInputStream());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 设置输入流长度为500
        objectMetadata.setContentLength(fileSize);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, basicPath + userid + "/" + filename, inputStream, objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        // 完成上传之后，关闭连接
        destory(cosClient);
        // 通过回调函数判断是否上传成功，有etag信息则表示上传成功，否则上传失败
        if (putObjectResult.getETag() != null) {
            inputStream.close();
            return true;
        } else
            return false;
    }

    public void deleteFileFromCOS(String filekey) {
        // 创建cos客户端
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(bucketRegion);
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);
        cosClient.deleteObject(bucketName, filekey);
    }

    public void destory(COSClient cosClient) {
        cosClient.shutdown();
    }
}
