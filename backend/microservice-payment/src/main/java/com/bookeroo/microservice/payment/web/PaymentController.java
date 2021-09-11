package com.bookeroo.microservice.payment.web;

import com.bookeroo.microservice.payment.model.Order;
import com.bookeroo.microservice.payment.service.PayPalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PayPalService payPalService;

    @Autowired
    public PaymentController(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    @PostMapping("/payment")
    public ResponseEntity<?> makePayment(@RequestBody Order order) {
        try {
            Payment payment = payPalService.createPayment(
                    order.getPrice(),
                    order.getCurrency(),
                    order.getMethod(),
                    order.getIntent(),
                    order.getDescription(),
                    // TODO success and cancel url?
                    "http://localhost:8080/payment/cancel",
                    "http://localhost:8080/payment/success");

            for (Links link : payment.getLinks())
                if (link.getRel().equals("approval_url"))
                    return new ResponseEntity<>(link.getHref(), HttpStatus.OK);
        } catch (PayPalRESTException ignored) {
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> cancelPayment() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/success")
    public ResponseEntity<?> successPayment(@RequestParam("paymentId") String paymentId, @RequestParam("payerId") String payerId) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved"))
                // TODO track payment history?
                return new ResponseEntity<>(HttpStatus.OK);
        } catch (PayPalRESTException ignored) {
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
