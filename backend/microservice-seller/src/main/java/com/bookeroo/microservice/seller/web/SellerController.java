package com.bookeroo.microservice.seller.web;

import com.bookeroo.microservice.seller.model.Listing;
import com.bookeroo.microservice.seller.model.ListingFormData;
import com.bookeroo.microservice.seller.model.SellerDetails;
import com.bookeroo.microservice.seller.security.JWTTokenProvider;
import com.bookeroo.microservice.seller.service.*;
import com.bookeroo.microservice.seller.validator.ListingFormDataValidator;
import com.bookeroo.microservice.seller.validator.SellerDetailsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bookeroo.microservice.seller.security.SecurityConstant.AUTHORIZATION_HEADER;
import static com.bookeroo.microservice.seller.security.SecurityConstant.JWT_SCHEME;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final UserService userService;
    private final BookService bookService;
    private final ListingService listingService;
    private final ListingFormDataValidator listingFormDataValidator;
    private final SellerDetailsService sellerDetailsService;
    private final SellerDetailsValidator sellerDetailsValidator;
    private final ValidationErrorService validationErrorService;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public SellerController(UserService userService,
                            BookService bookService,
                            ListingService listingService,
                            ListingFormDataValidator listingFormDataValidator,
                            SellerDetailsService sellerDetailsService,
                            SellerDetailsValidator sellerDetailsValidator,
                            ValidationErrorService validationErrorService,
                            JWTTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.bookService = bookService;
        this.listingService = listingService;
        this.listingFormDataValidator = listingFormDataValidator;
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

    @PostMapping("/add-listing")
    public ResponseEntity<?> addListing(@RequestHeader(AUTHORIZATION_HEADER) String tokenHeader,
                                        @Valid @RequestBody ListingFormData listingFormData,
                                        BindingResult result) {
        String jwt = tokenHeader.substring(JWT_SCHEME.length());
        listingFormDataValidator.validate(listingFormData, result);
        ResponseEntity<?> errorMap = validationErrorService.mapValidationErrors(result);
        if (errorMap != null)
            return errorMap;

        Listing listing = new Listing();
        listing.setPrice(listingFormData.getPrice());
        listing.setBook(bookService.getBookByIsbn(listingFormData.getIsbn()));
        listing.setSeller(userService.getUserByUsername(jwtTokenProvider.extractUsername(jwt)));
        return new ResponseEntity<>(listingService.addListing(listing), HttpStatus.OK);
    }

    @GetMapping("/get-listing/{id}")
    public ResponseEntity<?> getListing(@PathVariable long id) {
        return new ResponseEntity<>(listingService.getListingById(id), HttpStatus.OK);
    }

}
