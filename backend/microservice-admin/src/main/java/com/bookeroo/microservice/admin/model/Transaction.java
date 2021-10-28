package com.bookeroo.microservice.admin.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Transaction {

    @EmbeddedId
    private TransactionKey id;
    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId("listingId")
    @JoinColumn(name = "listing_id")
    @NotNull(message = "Listing for transaction cannot be null")
    private Listing listing;
    @ManyToOne
    @MapsId("buyerId")
    @JoinColumn(name = "user_id")
    @NotNull(message = "Buyer for transaction cannot be null")
    @JsonBackReference(value = "User_Transaction")
    private User buyer;
    private String orderId;
    private String captureId;
    private boolean isRefundable;
    private Date createdAt;
    private Date updatedAt;

    public Transaction() {
    }

    public TransactionKey getId() {
        return id;
    }

    public void setId(TransactionKey id) {
        this.id = id;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCaptureId() {
        return captureId;
    }

    public void setCaptureId(String captureId) {
        this.captureId = captureId;
    }

    public boolean isRefundable() {
        return isRefundable;
    }

    public void setRefundable(boolean refundable) {
        isRefundable = refundable;
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

}
