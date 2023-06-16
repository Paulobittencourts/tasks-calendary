package com.br.hbs.email.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class Configs {

    private static final int MAIL_OSAUR = 587;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(MAIL_OSAUR);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        return mailSender;
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
