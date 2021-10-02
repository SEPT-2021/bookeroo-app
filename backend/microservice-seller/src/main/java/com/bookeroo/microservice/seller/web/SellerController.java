package com.bookeroo.microservice.seller.web;

import com.bookeroo.microservice.seller.model.SellerDetails;
import com.bookeroo.microservice.seller.security.JWTTokenProvider;
import com.bookeroo.microservice.seller.service.BookService;
import com.bookeroo.microservice.seller.service.SellerDetailsService;
import com.bookeroo.microservice.seller.service.UserService;
import com.bookeroo.microservice.seller.service.ValidationErrorService;
import com.bookeroo.microservice.seller.validator.SellerDetailsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.bookeroo.microservice.seller.security.SecurityConstant.AUTHORIZATION_HEADER;
import static com.bookeroo.microservice.seller.security.SecurityConstant.JWT_SCHEME;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final UserService userService;
    private final BookService bookService;
    private final SellerDetailsService sellerDetailsService;
    private final SellerDetailsValidator sellerDetailsValidator;
    private final ValidationErrorService validationErrorService;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public SellerController(UserService userService,
                            BookService bookService,
                            SellerDetailsService sellerDetailsService,
                            SellerDetailsValidator sellerDetailsValidator,
                            ValidationErrorService validationErrorService,
                            JWTTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.bookService = bookService;
        this.sellerDetailsService = sellerDetailsService;
        this.sellerDetailsValidator = sellerDetailsValidator;
        this.validationErrorService = validationErrorService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerSeller(@RequestHeader(AUTHORIZATION_HEADER) String tokenHeader,
                                            @RequestBody SellerDetails sellerDetails,
                                            BindingResult result) {
        String jwt = tokenHeader.substring(JWT_SCHEME.length());
        sellerDetailsValidator.validate(sellerDetails, result);
        ResponseEntity<?> errorMap = validationErrorService.mapValidationErrors(result);
        if (errorMap != null)
            return errorMap;

        sellerDetails.setUser(userService.getUserByUsername(jwtTokenProvider.extractUsername(jwt)));
        return new ResponseEntity<>(sellerDetailsService.saveSellerDetails(sellerDetails), HttpStatus.OK);
    }

}
