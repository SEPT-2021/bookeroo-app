package com.bookeroo.microservice.book.model;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class BookFormData {

    @NotNull(message = "Title must not be null")
    private String title;
    @NotNull(message = "Author must not be null")
    private String author;
    @NotNull(message = "Page count must not be null")
    private long pageCount;
    @NotNull(message = "ISBN must not be null")
    private String isbn;
    @NotNull(message = "Price must not be null")
    private double price;
    @NotNull(message = "Description must not be null")
    private String description;
    private MultipartFile coverFile;
    private String coverUrl;

    public BookFormData() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getCoverFile() {
        return coverFile;
    }

    public void setCoverFile(MultipartFile coverFile) {
        this.coverFile = coverFile;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

}
