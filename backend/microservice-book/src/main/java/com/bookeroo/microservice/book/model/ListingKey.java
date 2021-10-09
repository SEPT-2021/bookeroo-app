package com.bookeroo.microservice.book.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ListingKey implements Serializable {

    @Column(name = "user_id")
    private long userId;
    @Column(name = "book_id")
    private long bookId;

    public ListingKey() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        ListingKey that = (ListingKey) object;

        if (userId != that.userId) return false;
        return bookId == that.bookId;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (bookId ^ (bookId >>> 32));
        return result;
    }

}
