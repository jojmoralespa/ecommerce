package com.demo.ecommerce.exceptions;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ObjectNotValidException extends RuntimeException{
    private final Map<String, List<String>> errorMessages;
}
