package com.example.useCase1BackEnd1.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfiguration {

    @Value("${endpoint}")
    private String dynamodbEndpoint;

    @Value("${region}")
    private String awsRegion;

    @Value("${access_key}")
    private String dynamodbAccessKey;

    @Value("${secret_access_key}")
    private String dynamodbSecretKey;

    public DynamoDBConfiguration(String dynamodbEndpoint, String awsRegion, String dynamodbAccessKey, String dynamodbSecretKey) {
        this.dynamodbEndpoint = dynamodbEndpoint;
        this.awsRegion = awsRegion;
        this.dynamodbAccessKey = dynamodbAccessKey;
        this.dynamodbSecretKey = dynamodbSecretKey;
    }


    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildAmazonDynamoDB());
    }

    private AmazonDynamoDB buildAmazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(dynamodbEndpoint,awsRegion))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(dynamodbAccessKey,dynamodbSecretKey)))
                .build();
    }
}