package com.example.Netflix.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

@Configuration
public class AwsConfig {

    @Value("{aws.accesskey}")
    private String accessKey;

    @Value("{aws.secretkey}")
    private String secretKey;

    @Bean
    public AwsCredentialsProvider getAwsCredentials(){
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey,secretKey));
    }

}
