package com.bookeroo.microservice.seller.model;

import javax.validation.constraints.NotBlank;

public class ListingFormData {

    @NotBlank(message = "Price cannot be null or blank")
    private String price;
    @NotBlank(message = "ISBN cannot be null or blank")
    private String isbn;

    public ListingFormData(String price, String isbn) {
        this.price = price;
        this.isbn = isbn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
