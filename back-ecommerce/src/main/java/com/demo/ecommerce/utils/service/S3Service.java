package com.demo.ecommerce.utils.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service implements FileServiceImpl {

    private final Logger log = LoggerFactory.getLogger(S3Service.class);

    @Value("${bucketName}")
    private String bucketName;

    private final AmazonS3 s3Client;

    @Override
    public String saveFile(MultipartFile file) {
        try {
            File fileConvert = convertMultiPartToFile(file);

            PutObjectResult putObjectResult = s3Client.putObject(bucketName, file.getOriginalFilename(), fileConvert);

            return putObjectResult.getContentMd5();
        } catch (Exception e) {
            log.error("Error trying to saveFile", e);
        }

        return null;
    }

    @Override
    public byte[] downloadFile(String fileName) {
        return new byte[0];
    }

    @Override
    public String deleteFile(String fileName) {
        return null;
    }

    @Override
    public List<String> listAllFiles() {
        return null;
    }


    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        return convertFile;
    }


}
