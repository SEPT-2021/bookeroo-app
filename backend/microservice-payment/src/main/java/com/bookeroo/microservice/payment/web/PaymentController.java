package com.bookeroo.microservice.payment.web;

import com.bookeroo.microservice.payment.model.CartCheckout;
import com.bookeroo.microservice.payment.model.User;
import com.bookeroo.microservice.payment.repository.UserRepository;
import com.bookeroo.microservice.payment.security.JWTTokenProvider;
import com.bookeroo.microservice.payment.service.PayPalService;
import com.bookeroo.microservice.payment.service.TransactionService;
import com.bookeroo.microservice.payment.service.ValidationErrorService;
import com.bookeroo.microservice.payment.validator.ShippingAddressValidator;
import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.payments.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static com.bookeroo.microservice.payment.security.SecurityConstant.AUTHORIZATION_HEADER;
import static com.bookeroo.microservice.payment.security.SecurityConstant.JWT_SCHEME;

/**
 * Controller to hold the microservice's endpoint implementations.
 */
@Controller
@RequestMapping("/api/orders")
public class PaymentController {

    private final PayPalService payPalService;
    private final TransactionService transactionService;
    private final UserRepository userRepository;
    private final JWTTokenProvider jwtTokenProvider;
    private final ShippingAddressValidator shippingAddressValidator;
    private final ValidationErrorService validationErrorService;

    @Autowired
    public PaymentController(PayPalService payPalService,
                             TransactionService transactionService,
                             UserRepository userRepository,
                             JWTTokenProvider jwtTokenProvider,
                             ShippingAddressValidator shippingAddressValidator,
                             ValidationErrorService validationErrorService) {
        this.payPalService = payPalService;
        this.transactionService = transactionService;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.shippingAddressValidator = shippingAddressValidator;
        this.validationErrorService = validationErrorService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkoutOrder(
            @RequestHeader(name = AUTHORIZATION_HEADER, required = false) String tokenHeader,
            @Valid @RequestBody CartCheckout cartCheckout,
            BindingResult result) {
        try {
            if (tokenHeader == null)
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            shippingAddressValidator.validate(cartCheckout.getShippingAddress(), result);
            ResponseEntity<?> errorMap = validationErrorService.mapValidationErrors(result);
            if (errorMap != null)
                return errorMap;

            String jwt = tokenHeader.substring(JWT_SCHEME.length());
            User buyer = userRepository.getByUsername(jwtTokenProvider.extractUsername(jwt));
            HttpResponse<Order> response = payPalService.createOrder(cartCheckout, buyer);
            LinkDescription approveLink = null;
            for (LinkDescription link : response.result().links())
                if (link.rel().equals("approve"))
                    approveLink = link;

            assert approveLink != null;
            transactionService.recordTransactions(cartCheckout, buyer, response.result().id());
            return new ResponseEntity<>(approveLink.href(), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable String orderId) {
        try {
            HttpResponse<Order> response = payPalService.getOrder(orderId);
            return ResponseEntity
                    .status(HttpStatus.valueOf(response.statusCode()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new Json().serialize(response.result()));
        } catch (IOException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/capture/{orderId}")
    public ResponseEntity<?> captureOrder(@PathVariable String orderId) {
        try {
            HttpResponse<Order> response = payPalService.captureOrder(orderId);
            return ResponseEntity
                    .status(HttpStatus.valueOf(response.statusCode()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new Json().serialize(response.result()));
        } catch (IOException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/refund/{listingId}")
    public ResponseEntity<?> refundOrder(@PathVariable long listingId) {
        try {
            HttpResponse<Refund> response = payPalService.refundOrder(listingId);
            return ResponseEntity
                    .status(HttpStatus.valueOf(response.statusCode()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new Json().serialize(response.result()));
        } catch (IOException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> getTransactionsByBuyer(
            @RequestHeader(name = AUTHORIZATION_HEADER, required = false) String tokenHeader) {
        if (tokenHeader == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        String jwt = tokenHeader.substring(JWT_SCHEME.length());
        User buyer = userRepository.getByUsername(jwtTokenProvider.extractUsername(jwt));
        return new ResponseEntity<>(transactionService.getTransactionsByBuyer(buyer.getId()), HttpStatus.OK);
    }

}
