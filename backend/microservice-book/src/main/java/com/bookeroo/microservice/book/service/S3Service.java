package com.bookeroo.microservice.book.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class S3Service {

    @Value("${aws.s3.bucket}")
    private String bucketName;
    private final AmazonS3 amazonS3;

    @Autowired
    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Async
    public void uploadFileAsync(String fileName, File file) {
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String uploadFile(MultipartFile multipartFile, String fileName) throws IOException {
        File file = convertMultiPartFileToFile(multipartFile);
        String uniqueFileName = getUniqueFileName(fileName)
                + "." + StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        uploadFileAsync(uniqueFileName, file);
        if (!file.delete())
            throw new IOException();

        return amazonS3.getUrl(bucketName, uniqueFileName).toExternalForm();
    }

    public String uploadFile(URL fileUrl, String fileName) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(fileUrl.openStream());
        File file = new File(getCleanFileName(fileName)
                + "." + StringUtils.getFilenameExtension(fileUrl.getFile()));
        FileOutputStream outputStream = new FileOutputStream(file);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.flush();
        outputStream.close();

        String uniqueFileName = getUniqueFileName(file.getName());
        uploadFileAsync(uniqueFileName, file);
        if (!file.delete())
            throw new IOException();

        return amazonS3.getUrl(bucketName, uniqueFileName).toExternalForm();
    }

    public ByteArrayOutputStream downloadFile(String fileUrl) throws IOException {
        S3Object object = amazonS3.getObject(new GetObjectRequest(bucketName, fileUrl));
        InputStream inputStream = object.getObjectContent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1)
            outputStream.write(buffer, 0, bytesRead);

        inputStream.close();
        outputStream.close();
        return outputStream;
    }

    private File convertMultiPartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()).replace(" ", "_"));
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(multipartFile.getBytes());
        outputStream.close();
        return file;
    }

    private String getUniqueFileName(String fileName) {
        return LocalDateTime.now() + "_" + fileName;
    }

    public String getCleanFileName(String fileName) {
        String replacement = "_";
        return fileName
                .replace("\\", replacement)
                .replace("/", replacement)
                .replace(":", replacement)
                .replace("*", replacement)
                .replace("?", replacement)
                .replace("\"", replacement)
                .replace("<", replacement)
                .replace(">", replacement)
                .replace("|", replacement);
    }

}
