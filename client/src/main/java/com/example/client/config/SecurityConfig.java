package com.example.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by niket.shah on 10/6/17.
 */

@Configuration
public class SecurityConfig {

    @Value("${client.keystore.file}")
    private Resource keyStoreFile;

    @Value("${client.keystore.password}")
    private String keyStorePassword;

    @Value("${client.truststore.file}")
    private Resource trustStoreFile;

    @Value("${client.truststore.password}")
    private String trustStorePassword;

    @PostConstruct
    public void setProperites() throws IOException {
        System.setProperty("javax.net.ssl.trustStore", trustStoreFile.getFile().getAbsolutePath());
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
        System.setProperty("javax.net.ssl.keyStore",  keyStoreFile.getFile().getAbsolutePath());
        System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.build();
    }
}
