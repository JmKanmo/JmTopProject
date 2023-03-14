package com.jmshop.jmshop_admin.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.jmshop.jmshop_admin.config.AwsS3Config;
import com.jmshop.jmshop_admin.dto.error.type.AwsS3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * whatap 개발 시에는 기존에 개발된 라이브러리 사용 여부 결정
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AwsS3Util {
    private final AwsS3Config awsS3Config;

    public void uploadSourceMap(String fullName, byte[] data) throws AwsS3Exception {
        AmazonS3 amazonS3 = awsS3Config.getAmazonS3();

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data)) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("application/x-gzip");
            PutObjectRequest putObjectRequest = new PutObjectRequest(awsS3Config.getBucketName(), fullName, byteArrayInputStream, objectMetadata);
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            log.error("AwsS3Util:storeSourceMap: exception:", e);
            throw new AwsS3Exception("s3에 데이터를 전송 중에 에러가 발생하였습니다.");
        }
    }

    public void removeSourceMap(String targetDir) throws AwsS3Exception {
        try {
            AmazonS3 amazonS3 = awsS3Config.getAmazonS3();

            for (S3ObjectSummary file : getS3ObjectSummaries(targetDir)) {
                amazonS3.deleteObject(awsS3Config.getBucketName(), file.getKey());
            }
        } catch (Exception e) {
            log.error("AwsS3Util:removeSourceMap: exception:", e);
            throw new AwsS3Exception("s3에 데이터를 삭제 중에 에러가 발생하였습니다.");
        }
    }

    public List<byte[]> readSourceMapList(String dir) throws AwsS3Exception {
        try {
            List<byte[]> sourceMapList = new ArrayList<>();
            AmazonS3 amazonS3 = awsS3Config.getAmazonS3();

            for (S3ObjectSummary s3ObjectSummary : getS3ObjectSummaries(dir)) {
                S3Object s3Object = amazonS3.getObject(awsS3Config.getBucketName(), s3ObjectSummary.getKey());
                try (S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent()) {
                    sourceMapList.add(s3ObjectInputStream.readAllBytes());
                } catch (RuntimeException runtimeException) {
                    throw runtimeException;
                }
            }
            return sourceMapList;
        } catch (Exception e) {
            log.error("AwsS3Util:removeSourceMap: exception:", e);
            throw new AwsS3Exception("s3에 데이터를 조회 및 반환 중에 에러가 발생하였습니다.");
        }
    }

    public byte[] readSourceMap(String dir) throws AwsS3Exception {
        try {
            AmazonS3 amazonS3 = awsS3Config.getAmazonS3();
            S3Object s3Object = amazonS3.getObject(awsS3Config.getBucketName(), dir);

            try (S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent()) {
                return s3ObjectInputStream.readAllBytes();
            } catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
        } catch (Exception e) {
            log.error("AwsS3Util:removeSourceMap: exception:", e);
            throw new AwsS3Exception("s3에 데이터를 조회 및 반환 중에 에러가 발생하였습니다.");
        }
    }

    public int getS3FileSizeByDir(String dir) throws AwsS3Exception {
        AmazonS3 amazonS3 = awsS3Config.getAmazonS3();
        int fileSize = 0;

        for (S3ObjectSummary s3ObjectSummary : getS3ObjectSummaries(dir)) {
            fileSize += s3ObjectSummary.getSize();
        }
        return fileSize;
    }

    private List<S3ObjectSummary> getS3ObjectSummaries(String dir) {
        AmazonS3 amazonS3 = awsS3Config.getAmazonS3();
        return amazonS3.listObjects(awsS3Config.getBucketName(), dir).getObjectSummaries();
    }
}
