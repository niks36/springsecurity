package com.example.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;

/**
 * Created by niket.shah on 10/6/17.
 */

@Configuration
public class SecurityConfig {

    @Value("${client.keystore.file}")
    private String keyStoreFile;

    @Value("${client.keystore.password}")
    private String keyStorePassword;

    @Value("${client.truststore.file}")
    private String trustStoreFile;

    @Value("${client.truststore.password}")
    private String trustStorePassword;

    @PostConstruct
    public void setProperites(){
        System.setProperty("javax.net.ssl.trustStore", SecurityConfig.class.getClassLoader().getResource(trustStoreFile).getFile());
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
        System.setProperty("javax.net.ssl.keyStore",  SecurityConfig.class.getClassLoader().getResource(keyStoreFile).getFile());
        System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);


        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                (hostname, sslSession) -> hostname.equals("localhost"));
    }
}
