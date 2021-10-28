package com.bookeroo.microservice.book.web;

import com.bookeroo.microservice.book.model.Listing;
import com.bookeroo.microservice.book.model.ListingFormData;
import com.bookeroo.microservice.book.model.User;
import com.bookeroo.microservice.book.repository.UserRepository;
import com.bookeroo.microservice.book.security.JWTTokenProvider;
import com.bookeroo.microservice.book.service.ListingService;
import com.bookeroo.microservice.book.service.ValidationErrorService;
import com.bookeroo.microservice.book.validator.ListingFormDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.bookeroo.microservice.book.security.SecurityConstant.AUTHORIZATION_HEADER;
import static com.bookeroo.microservice.book.security.SecurityConstant.JWT_SCHEME;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final UserRepository userRepository;
    private final ListingService listingService;
    private final ListingFormDataValidator listingFormDataValidator;
    private final ValidationErrorService validationErrorService;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public ListingController(UserRepository userRepository,
                             ListingService listingService,
                             ListingFormDataValidator listingFormDataValidator,
                             ValidationErrorService validationErrorService,
                             JWTTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.listingService = listingService;
        this.listingFormDataValidator = listingFormDataValidator;
        this.validationErrorService = validationErrorService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewListing(
            @RequestHeader(name = AUTHORIZATION_HEADER, required = false) String tokenHeader,
            @Valid @RequestBody ListingFormData formData, BindingResult result) {
        if (tokenHeader == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        String username = jwtTokenProvider.extractUsername(tokenHeader.substring(JWT_SCHEME.length()));
        listingFormDataValidator.validate(formData, result);
        ResponseEntity<?> errorMap = validationErrorService.mapValidationErrors(result);
        if (errorMap != null)
            return errorMap;

        return new ResponseEntity<>(listingService.saveListing(formData, username), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getListing(@PathVariable long id) {
        return new ResponseEntity<>(listingService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateListing(@PathVariable long id, @RequestBody Listing updatedListing) {
        return new ResponseEntity<>(listingService.updateListing(id, updatedListing), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteListing(@PathVariable long id) {
        listingService.removeListing(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/book")
    public ResponseEntity<?> getBookForListing(@PathVariable long id) {
        Listing listing = listingService.getById(id);
        Map<String, Long> bookId = new HashMap<>();
        bookId.put("id", listing.getBook().getId());
        return new ResponseEntity<>(bookId, HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> getAllByBook(@PathVariable("id") long id) {
        return new ResponseEntity<>(listingService.getAllByBook(id), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllByUser(
        @RequestHeader(name = AUTHORIZATION_HEADER, required = false) String tokenHeader) {
        if (tokenHeader == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        String jwt = tokenHeader.substring(JWT_SCHEME.length());
        User user = userRepository.getByUsername(jwtTokenProvider.extractUsername(jwt));
        return new ResponseEntity<>(listingService.getAllByUser(user.getId()), HttpStatus.OK);
    }

}
