package com.bookeroo.microservice.book.payload;
import javax.validation.constraints.NotBlank;

public class searchrequest {

    @NotBlank(message = "id cannot be blank")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
