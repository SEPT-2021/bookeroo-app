package com.bookeroo.microservice.book.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Author cannot be blank")
    private String author;
    @NotNull(message = "Number of pages cannot be null")
    private long pageCount;
    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;
    @NotNull(message = "Price cannot be null")
    private double price;
    @NotBlank(message = "Books are required to have a brief description")
    @Size(max = 8191)
    private String description;
    @NotBlank(message = "Books must have a cover")
    @Size(max = 1023)
    private String cover;
    private Date createdAt;
    private Date updatedAt;

    public Book() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;

        Book book = (Book) object;
        return id == book.id
                && pageCount == book.pageCount
                && Double.compare(book.price, price) == 0
                && title.equals(book.title)
                && author.equals(book.author)
                && isbn.equals(book.isbn)
                && description.equals(book.description)
                && cover.equals(book.cover)
                && createdAt.equals(book.createdAt)
                && updatedAt.equals(book.updatedAt);
    }

    @Override
    public String toString() {
        return String.format("Book {\n" +
                "\tid: \"%s\",\n" +
                "\ttitle: \"%s\"\n" +
                "\tauthor: \"%s\"\n" +
                "\tpageCount: \"%s\"\n" +
                "\tisbn: \"%s\"\n" +
                "\tcover: \"%s\"\n" +
                "\tprice: \"%.2f\"\n" +
                "\tdescription: \"%s\"\n" +
                "}", id, title, author, pageCount, isbn, cover, price, description);
    }

}
