package com.example.qiniu_img_uploader;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

@Component
public class QiniuUtils {

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private Auth auth;

    @Value("${qiniu.bucket}")
    private String bucket;
    @Value("${qiniu.path}")
    private String path;

    public  String uploadImg(FileInputStream fis, String key) {

        try {
//            Auth auth = Auth.create(accessKey, secretKey);
            String token = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(fis, key, token, null, null);
                DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                String returnPath = path + "/" + defaultPutRet.key;
                return returnPath;
            } catch (QiniuException ex) {
                Response response = ex.response;
                System.err.println(response.toString());
                try {
                    System.err.println(response.bodyString());
                } catch (QiniuException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
