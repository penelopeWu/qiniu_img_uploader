package com.example.qiniu_img_uploader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    @RequestMapping("upload")
    public boolean upload(@RequestParam("file")MultipartFile file) throws IOException {
        FileInputStream fis = (FileInputStream) file.getInputStream();
        String path = qiniuUtils.uploadImg(fis, UUID.randomUUID().toString());
        return path != null;
    }
}
