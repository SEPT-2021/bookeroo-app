package com.bookeroo.microservice.book.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

@Entity
public class Review {

    @JsonIgnore
    public static final List<Integer> RATING_VALUES = Arrays.asList(1, 2, 3, 4, 5);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonBackReference(value = "Book_Review")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "User_Review")
    private User user;
    private String userFullName;
    @Size(max = 280, message = "Text must not be longer than 280 characters")
    private String text;
    private int rating;

    public Review() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String reviewer) {
        this.userFullName = reviewer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
