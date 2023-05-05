package com.demo.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {


    @Value("${passwordEmail}")
    private String password;

    @Value("${userEmail}")
    private String user;

    @Bean
    public JavaMailSender getJavaMailSender() {

        //Este objeto nos va ayudar a configurar todas las propiedades
        //de nuestro "enviador de correos"
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        //Define host for electronic email provider (GMAIL)
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(user);
        mailSender.setPassword(password);


        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp"); //Define the protocol to use
        props.put("mail.smtp.auth", "true"); //when the app use the protocol with must authenticate with user y password defined above
        props.put("mail.smtp.starttls.enable", "true"); // We cipher all the communication with the provider all time long
        props.put("mail.debug", "true"); //Just for Dev or QA enviroment


        return mailSender;
    }

}
