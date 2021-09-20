package com.bookeroo.microservice.book.exception;

/**
 * Response body presented when {@link S3UploadFailureException} is thrown.
 */
public class S3UploadFailureResponse {

    private String cover;

    public S3UploadFailureResponse(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

}
