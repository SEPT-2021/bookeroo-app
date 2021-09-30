package com.bookeroo.microservice.payment.model;

import javax.validation.constraints.NotBlank;

/**
 * Shipping address data model, contains information to ship orders to.
 */
public class ShippingAddress {

    @NotBlank(message = "Address line 1 cannot be null or blank")
    private String addressLine1;
    private String addressLine2;
    @NotBlank(message = "City cannot be null or blank")
    private String city;
    @NotBlank(message = "State cannot be null or blank")
    private String state;
    @NotBlank(message = "Postal code cannot be null or blank")
    private String postalCode;

    public ShippingAddress() {
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
