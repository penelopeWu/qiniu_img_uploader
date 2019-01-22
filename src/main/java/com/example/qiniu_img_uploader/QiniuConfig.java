package com.example.qiniu_img_uploader;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("qiniu")
public class QiniuConfig {

    private String accessKey;

    private String secretKey;


    @Bean
    public com.qiniu.storage.Configuration configuration() {
        return new com.qiniu.storage.Configuration(Zone.zone0());
    }

    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(configuration());
    }

    @Bean
    public Auth auth() {
        return Auth.create(accessKey, secretKey);
    }

    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(), configuration());
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

}
