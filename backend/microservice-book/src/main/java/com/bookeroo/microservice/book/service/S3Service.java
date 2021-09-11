package com.bookeroo.microservice.book.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class S3Service  {

    @Value("${aws.s3.bucket}")
    private String bucketName = "bookeroo-test-bucket";
    private final AmazonS3 amazonS3;

    @Autowired
    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartFileToFile(multipartFile);
        String uniqueFileName = LocalDateTime.now() + "_" + file.getName();
        uploadFileToS3Bucket(uniqueFileName, file);
        if (!file.delete())
            throw new IOException();

        return uniqueFileName;
    }

    public ByteArrayOutputStream downloadFile(String url) throws IOException {
        S3Object object = amazonS3.getObject(new GetObjectRequest(bucketName, url));
        InputStream inputStream = object.getObjectContent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length;
        byte[] buffer = new byte[4096];
        while ((length = inputStream.read(buffer, 0, buffer.length)) != -1)
            outputStream.write(buffer, 0, length);

        return outputStream;
    }

    private File convertMultiPartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()).replace(" ", "_"));
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(multipartFile.getBytes());
        outputStream.close();
        return file;
    }

    @Async
    public void uploadFileToS3Bucket(String fileName, File file) {
        amazonS3.putObject(new PutObjectRequest(this.bucketName, fileName, file));
    }

}
