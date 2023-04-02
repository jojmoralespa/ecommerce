package com.demo.ecommerce.utils.web_controller;

import com.demo.ecommerce.utils.service.S3Service;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class S3Controller {

    private final Logger log = LoggerFactory.getLogger(S3Controller.class);


    private final S3Service s3Service;

    @PostMapping("/s3/save")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {

        if (file == null) {
            log.warn("Trying to uploadFile without a multipartFile");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(s3Service.saveFile(file));

    }


}
