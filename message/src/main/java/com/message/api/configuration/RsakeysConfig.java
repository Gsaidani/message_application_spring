package com.message.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
@Component
@PropertySource("classpath:rsa.properties")
public class RsakeysConfig {
    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    public RsakeysConfig(@Value("${rsa.public-key}") String publicKey, @Value("${rsa.public-key}")String privateKey) {
        this.publicKey = RsaKeyConverters.x509().convert(new ByteArrayInputStream(publicKey.getBytes()));
        this.privateKey = RsaKeyConverters.pkcs8().convert(new ByteArrayInputStream(privateKey.getBytes()));
        System.out.println("publicKey : "+publicKey);
        System.out.println("privateKey : "+privateKey);

    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }
}
