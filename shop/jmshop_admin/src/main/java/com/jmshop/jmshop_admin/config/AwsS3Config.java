package com.jmshop.jmshop_admin.config;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "yaml")
@PropertySource(value = "classpath:application-util.yml", factory = YamlPropertySourceFactory.class)
@Data
public class AwsS3Config {
    @Value("${aws_s3.access_key_id}")
    private String accessKeyId;

    @Value("${aws_s3.secret_access_key}")
    private String secretAccessKey;

    @Value("${aws_s3.bucket_name}")
    private String bucketName;

    private AmazonS3 amazonS3;

    @PostConstruct
    private void initializeAmazon() {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(accessKeyId, secretAccessKey)))
                .withRegion(Regions.US_EAST_1)
                .build();
    }
}
