package com.demo.ecommerce.utils.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import com.demo.ecommerce.utils.aws.AmazonS3File;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service implements FileServiceImpl {

    private final Logger log = LoggerFactory.getLogger(S3Service.class);

    @Value("${bucketName}")
    private String bucketName;

    private final AmazonS3 s3Client;

    @Override
    public AmazonS3File uploadFile(MultipartFile file) {
        try {
            InputStream fileConvert = file.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize()); // Definir la longitud de la data a subir para evitar errores

            PutObjectRequest putObjectResult = new PutObjectRequest(bucketName, file.getOriginalFilename(), fileConvert, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead); //Fijar publico acceso de lectura al bucket

            s3Client.putObject(putObjectResult);

            return new AmazonS3File().setUrl(s3Client.getUrl(bucketName, file.getOriginalFilename()).toString());
        } catch (Exception e) {
            log.error("Error trying to saveFile", e);
        }

        return null;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        return convertFile;
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


}
