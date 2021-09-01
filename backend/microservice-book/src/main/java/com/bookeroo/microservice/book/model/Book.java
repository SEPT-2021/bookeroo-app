package com.bookeroo.microservice.book.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "title cannot be blank")
    private String title;

    @NotBlank(message = "Authour cannot be blank")
    private String author;

    @NotBlank(message = "book cannot be empty")
    private String pages;

    @NotBlank(message = "ISBN must be valid")
    private String ISBN;

    public Book(){

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

    public void setAuthor(String authour) {
        this.author = authour;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }



    @Override
    @JsonIgnore
    public String toString() {
        return String.format("User {\n" +
                "\tid: \"%s\",\n" +
                "\ttitle: \"%s\"\n" +
                "\tauthour: \"%s\"\n" +
                "\tpages: \"%s\"\n" +
                "\tISBN: \"%s\"\n" +
                "}", id, title, author, pages, ISBN);
    }
}
