package com.bookeroo.microservice.payment.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

/**
 * Book JPA entity to represent the book data model.
 */
@Entity
public class Book {

    public enum BookCondition {
        NEW("New"),
        FINE("Fine"),
        VERY_GOOD("Very Good"),
        FAIR("Fair"),
        POOR("Poor");

        public String name;

        BookCondition(String name) {
            this.name = name;
        }
    }

    public enum BookCategory {
        LITERARY_FICTION("Literary Fiction"),
        MYSTERY("Mystery"),
        THRILLER("Thriller"),
        HORROR("Horror"),
        HISTORICAL("Historical"),
        ROMANCE("Romance"),
        WESTERN("Western"),
        BILDUNGSROMAN("Bildungsroman"),
        SPECULATIVE_FICTION("Speculative Fiction"),
        SCIENCE_FICTION("Science Fiction"),
        FANTASY("Fantasy"),
        DYSTOPIAN("Dystopian"),
        MAGICAL_REALISM("Magical Realism"),
        REALIST_LITERATURE("Realist Literature"),
        OTHER("Other");

        public String name;

        BookCategory(String name) {
            this.name = name;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Author cannot be blank")
    private String author;
    @NotBlank(message = "Number of pages cannot be blank")
    private String pageCount;
    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;
    @NotBlank(message = "Books are required to have a brief description")
    @Size(max = 8191)
    private String description;
    @NotBlank(message = "Price cannot be blank")
    private String price;
    @NotBlank(message = "Condition cannot be blank")
    private String bookCondition;
    @NotBlank(message = "Category cannot be blank")
    private String bookCategory;
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

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(String bookCondition) {
        this.bookCondition = bookCondition;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
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

        if (id != book.id)
            return false;
        if (!Objects.equals(title, book.title))
            return false;
        if (!Objects.equals(author, book.author))
            return false;
        if (!Objects.equals(pageCount, book.pageCount))
            return false;
        if (!Objects.equals(isbn, book.isbn))
            return false;
        if (!Objects.equals(description, book.description))
            return false;
        if (!Objects.equals(price, book.price))
            return false;
        if (!Objects.equals(bookCondition, book.bookCondition))
            return false;
        if (!Objects.equals(bookCategory, book.bookCategory))
            return false;

        return Objects.equals(cover, book.cover);
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
                "\tdescription: \"%s\"\n" +
                "\tprice: \"%s\"\n" +
                "\tcondition: \"%s\"\n" +
                "\tcategory: \"%s\"\n" +
                "}", id, title, author, pageCount, isbn, cover, description, price, bookCondition, bookCategory);
    }

}
