package com.bookeroo.microservice.payment.web;

import com.bookeroo.microservice.payment.service.PayPalService;
import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/api/orders")
public class PaymentController {

    private final PayPalService payPalService;

    @Autowired
    public PaymentController(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    @GetMapping("/create")
    public ResponseEntity<?> createOrder() {
        try {
            HttpResponse<Order> response = payPalService.createOrder();
            return ResponseEntity
                    .status(HttpStatus.valueOf(response.statusCode()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new Json().serialize(response.result()));
        } catch (IOException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getOrder(@PathVariable String id) {
        try {
            HttpResponse<Order> response = payPalService.getOrder(id);
            return ResponseEntity
                    .status(HttpStatus.valueOf(response.statusCode()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new Json().serialize(response.result()));
        } catch (IOException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/capture/{id}")
    public ResponseEntity<?> captureOrder(@PathVariable String id) {
        try {
            HttpResponse<Order> response = payPalService.captureOrder(id);
            return ResponseEntity
                    .status(HttpStatus.valueOf(response.statusCode()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new Json().serialize(response.result()));
        } catch (IOException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
