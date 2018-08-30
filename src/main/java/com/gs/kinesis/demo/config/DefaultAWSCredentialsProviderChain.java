package com.gs.kinesis.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

@Component
public class DefaultAWSCredentialsProviderChain implements AWSCredentialsProvider {

    @Value("${aws.access.key}")
    private String accessKey;

    @Value("${aws.secret.key}")
    private String secretKey;

    public AWSCredentials getCredentials() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return credentials;
    }

    public void refresh() {

    }
}
