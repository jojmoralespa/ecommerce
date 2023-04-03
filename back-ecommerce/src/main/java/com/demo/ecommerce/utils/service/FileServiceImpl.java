package com.demo.ecommerce.utils.service;

import com.demo.ecommerce.utils.aws.AmazonS3File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileServiceImpl {

    AmazonS3File uploadFile(MultipartFile file);

    byte[] downloadFile(String fileName);

    String deleteFile(String fileName);

    List<String> listAllFiles();

}
