package com.bookeroo.microservice.book.exception;

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
