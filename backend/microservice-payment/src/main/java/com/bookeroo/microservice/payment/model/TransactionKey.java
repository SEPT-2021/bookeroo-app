package com.bookeroo.microservice.payment.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TransactionKey implements Serializable {

    private long listingId;
    private long buyerId;

    public TransactionKey() {
    }

    public TransactionKey(long listingId, long buyerId) {
        this.listingId = listingId;
        this.buyerId = buyerId;
    }

    public long getListingId() {
        return listingId;
    }

    public void setListingId(long listingId) {
        this.listingId = listingId;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        TransactionKey that = (TransactionKey) object;

        if (listingId != that.listingId) return false;
        return buyerId == that.buyerId;
    }

    @Override
    public int hashCode() {
        int result = (int) (listingId ^ (listingId >>> 32));
        result = 31 * result + (int) (buyerId ^ (buyerId >>> 32));
        return result;
    }
}
