package com.demo.ecommerce.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountNotConfirmedException extends Exception {

    private Boolean resendEmail;


}