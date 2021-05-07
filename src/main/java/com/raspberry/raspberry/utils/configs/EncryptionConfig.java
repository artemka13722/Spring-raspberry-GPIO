package com.raspberry.raspberry.utils.configs;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.ZeroSaltGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfig {

    @Value("${secret.key}")
    private String key;

    @Bean
    public StringEncryptor stringEncryptor() {
        StandardPBEStringEncryptor stringEncryptor = new StandardPBEStringEncryptor();
        stringEncryptor.setSaltGenerator(new ZeroSaltGenerator()); // для уменьшения длины кодового слова
        stringEncryptor.setPassword(key);
        stringEncryptor.initialize();
        return stringEncryptor;
    }

}
