package com.bookeroo.microservice.admin.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
public class SellerDetails {

    @Id
    private long id;
    @OneToOne
    @MapsId
    @JoinColumn
    @JsonBackReference(value = "User_SellerDetails")
    private User user;
    @NotBlank(message = "ABN cannot be blank")
    private String abn;
    @NotBlank(message = "Business name cannot be blank")
    private String businessName;
    @NotBlank(message = "Business phone cannot be blank")
    @Pattern(regexp = "(^(\\+?\\(61\\)|\\(\\+?61\\)|\\+?61|\\(0[1-9]\\)|0[1-9])?( ?-?[0-9]){7,9}$)", message = "Not a valid phone number")
    private String businessPhone;
    private Date createdAt;
    private Date updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAbn() {
        return abn;
    }

    public void setAbn(String abn) {
        this.abn = abn;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String phoneNumber) {
        this.businessPhone = phoneNumber;
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
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        SellerDetails that = (SellerDetails) object;

        if (id != that.id) return false;
        if (!abn.equals(that.abn)) return false;
        if (!businessName.equals(that.businessName)) return false;
        return businessPhone.equals(that.businessPhone);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (abn != null ? abn.hashCode() : 0);
        result = 31 * result + (businessName != null ? businessName.hashCode() : 0);
        result = 31 * result + (businessPhone != null ? businessPhone.hashCode() : 0);
        return result;
    }

}
