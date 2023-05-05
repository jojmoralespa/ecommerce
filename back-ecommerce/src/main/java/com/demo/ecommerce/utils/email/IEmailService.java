package com.demo.ecommerce.utils.email;

import java.io.File;

public interface IEmailService {

    void sendEmail(String[] toUser, String subject, String message, Boolean isHTML);


    void sendEmailWithFile(String[] toUser, String subject, String message, File file);


}
