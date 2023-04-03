package com.demo.ecommerce.utils.aws;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AmazonS3File {

    private String fileName;
    private String url;
    private String key;

    public AmazonS3File(String name, String domain, String path) {
        this.fileName = name;
        this.url = domain + path;
        this.key = path;
    }

    public AmazonS3File() {
    }




}
