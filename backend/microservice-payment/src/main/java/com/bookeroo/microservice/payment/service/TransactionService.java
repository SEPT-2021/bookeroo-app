package com.bookeroo.microservice.payment.service;

import com.bookeroo.microservice.payment.model.*;
import com.bookeroo.microservice.payment.repository.ListingRepository;
import com.bookeroo.microservice.payment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.bookeroo.microservice.payment.config.PaymentConstants.REFUND_EXPIRATION_TIME_HOURS;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ListingRepository listingRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, ListingRepository listingRepository) {
        this.transactionRepository = transactionRepository;
        this.listingRepository = listingRepository;
    }

    private void recordTransaction(Listing listing, User buyer, String orderId) {
        Transaction transaction = new Transaction();
        TransactionKey transactionId = new TransactionKey();
        transactionId.setListingId(listing.getId());
        transactionId.setBuyerId(buyer.getId());
        transaction.setId(transactionId);
        transaction.setListing(listing);
        transaction.setBuyer(buyer);
        transaction.setOrderId(orderId);

        transactionRepository.save(transaction);
    }

    public void recordTransactions(CartCheckout cartCheckout, User buyer, String orderId) {
        for (Listing listing : cartCheckout.getOrderItems())
            recordTransaction(listing, buyer, orderId);
    }

    public List<Transaction> getTransactionsByBuyer(long buyerId) {
        List<Transaction> transactions = transactionRepository.findByBuyer_Id(buyerId);
        transactions.forEach(transaction -> {
            long elapsedTimeMillis =
                    new Date().getTime() - transaction.getCreatedAt().getTime();
            transaction.setRefundable(
                    transaction.isRefundable() &&
                    TimeUnit.HOURS.convert(elapsedTimeMillis, TimeUnit.MILLISECONDS) < REFUND_EXPIRATION_TIME_HOURS);
        });

        return transactions;
    }

}
