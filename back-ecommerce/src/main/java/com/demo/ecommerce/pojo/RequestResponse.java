package com.demo.ecommerce.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
public class RequestResponse {
    private Boolean successful;
    private Object body;
    private Map<String, List<String>> errorMessages;

    public RequestResponse(Object body) {
        this.successful = true;
        this.body = body;
        this.errorMessages = null;
    }

    public RequestResponse(Map<String, List<String>> errorMessages) {
        this.successful = false;
        this.body = null;
        this.errorMessages = errorMessages;
    }


}
