package com.bookeroo.microservice.payment.web;

import com.bookeroo.microservice.payment.model.CartCheckout;
import com.bookeroo.microservice.payment.model.User;
import com.bookeroo.microservice.payment.repository.UserRepository;
import com.bookeroo.microservice.payment.security.JWTTokenProvider;
import com.bookeroo.microservice.payment.service.PayPalService;
import com.bookeroo.microservice.payment.service.ValidationErrorService;
import com.bookeroo.microservice.payment.validator.ShippingAddressValidator;
import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    private final UserRepository userRepository;
    private final JWTTokenProvider jwtTokenProvider;
    private final ShippingAddressValidator shippingAddressValidator;
    private final ValidationErrorService validationErrorService;

    @Autowired
    public PaymentController(PayPalService payPalService,
                             UserRepository userRepository,
                             JWTTokenProvider jwtTokenProvider,
                             ShippingAddressValidator shippingAddressValidator,
                             ValidationErrorService validationErrorService) {
        this.payPalService = payPalService;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.shippingAddressValidator = shippingAddressValidator;
        this.validationErrorService = validationErrorService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkoutOrder(
            @RequestHeader(AUTHORIZATION_HEADER) String tokenHeader,
            @RequestBody CartCheckout cartCheckout,
            BindingResult result) {
        try {
            shippingAddressValidator.validate(cartCheckout.getShippingAddress(), result);
            ResponseEntity<?> errorMap = validationErrorService.mapValidationErrors(result);
            if (errorMap != null)
                return errorMap;

            String jwt = tokenHeader.substring(JWT_SCHEME.length());
            User user = userRepository.getByUsername(jwtTokenProvider.extractUsername(jwt));
            HttpResponse<Order> response = payPalService.createOrder(user, cartCheckout);
            LinkDescription approveLink = null;
            for (LinkDescription link : response.result().links()) {
                if (link.rel().equals("approve"))
                    approveLink = link;
            }

            assert approveLink != null;
            return new ResponseEntity<>(approveLink.href(), HttpStatus.OK);
        } catch (Exception exception) {
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
