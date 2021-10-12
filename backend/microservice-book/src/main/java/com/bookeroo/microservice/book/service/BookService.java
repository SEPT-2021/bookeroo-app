package com.bookeroo.microservice.book.service;

import com.bookeroo.microservice.book.exception.BookFormDataValidationException;
import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.exception.S3UploadFailureException;
import com.bookeroo.microservice.book.exception.UserNotFoundException;
import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.model.BookFormData;
import com.bookeroo.microservice.book.model.Listing;
import com.bookeroo.microservice.book.model.User;
import com.bookeroo.microservice.book.repository.BookRepository;
import com.bookeroo.microservice.book.repository.ListingRepository;
import com.bookeroo.microservice.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

/**
 * Service layer for the {@link Book} JPA entity.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final S3Service s3Service;

    @Autowired
    public BookService(BookRepository bookRepository,
                       UserRepository userRepository,
                       ListingRepository listingRepository,
                       S3Service s3Service) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
        this.s3Service = s3Service;
    }

    public Book saveBook(BookFormData formData, String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UserNotFoundException(String.format("User by username %s not found", username)));
        Book existingBook = bookRepository.findByTitleAndAuthorAndIsbn(
                formData.getTitle(), formData.getAuthor(), formData.getIsbn());

        Book book;
        if (existingBook == null) {
            book = new Book();
            book.setTitle(formData.getTitle());
            book.setAuthor(formData.getAuthor());
            book.setPageCount(String.valueOf(formData.getPageCount()));
            book.setIsbn(formData.getIsbn());
            book.setDescription(formData.getDescription());
            book.setBookCategory(formData.getCategory().name());

            try {
                MultipartFile coverFile = formData.getCoverFile();
                if (coverFile != null)
                    book.setCover(s3Service.uploadFile(formData.getCoverFile(), formData.getTitle()));
                else
                    book.setCover(s3Service.uploadFile(new URL(formData.getCoverUrl()), formData.getTitle()));

                book = bookRepository.save(book);
            } catch (ConstraintViolationException exception) {
                throw new BookFormDataValidationException(
                        exception.getConstraintViolations().iterator().next().getMessage());
            } catch (IOException exception) {
                throw new S3UploadFailureException(exception.getMessage());
            } catch (Exception exception) {
                throw new BookFormDataValidationException(exception.getMessage());
            }
        } else {
            book = existingBook;
        }

        Listing listing = new Listing();
        listing.setUser(user.get());
        listing.setBook(book);
        listing.setPrice(formData.getPrice());
        listing.setBookCondition(formData.getCondition().name());
        listing.setAvailable(true);
        listingRepository.save(listing);

        book = bookRepository.findById(book.getId()).orElse(book);
        if (book.getListings() == null)
            book.setListings(Collections.singletonList(listing));

        return book;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBook(long id) {
        Optional<Book> book = bookRepository.findById(id);
        book.orElseThrow(() -> new BookNotFoundException(String.format("Book by id %s not found", id)));
        book.get().getListings().removeIf(listing -> !listing.isAvailable());

        return book.get();
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book updateBook(long id, Book updatedBook) {
        Optional<Book> existing = bookRepository.findById(id);
        existing.orElseThrow(() -> new BookNotFoundException(String.format("Book by id %s not found", id)));
        Book book = existing.get();

        for (Field field : Book.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.get(updatedBook) != null) {
                    field.set(book, (!field.getType().isPrimitive()
                            && !Arrays.asList("listings", "createdAt", "updatedAy").contains(field.getName()))
                            ? field.get(updatedBook)
                            : field.get(book));
                }
            } catch (IllegalAccessException ignored) {
            }
        }

        return book;
    }

    public void removeBook(long id) {
        if (!bookRepository.existsById(id))
            throw new BookNotFoundException(String.format("Book by id %s not found", id));

        bookRepository.deleteById(id);
    }

    public List<Book> searchBookByTitle(String title) {
        List<Book> results = new ArrayList<>();
        bookRepository.findByTitleContaining(title).forEach(results::add);

        return results;
    }

    public List<Book> searchBookByAuthor(String author) {
        List<Book> results = new ArrayList<>();
        bookRepository.findByAuthorContaining(author).forEach(results::add);

        return results;
    }

    public List<Book> searchBookByIsbn(String isbn) {
        List<Book> results = new ArrayList<>();
        bookRepository.findByIsbnContaining(isbn).forEach(results::add);

        return results;
    }

    public List<Book> searchBookByCategory(String category) {
        List<Book> results = new ArrayList<>();
        bookRepository.findByBookCategoryContaining(category).forEach(results::add);

        return results;
    }

    public Set<Book> searchBookByKeyword(String keyword) {
        Set<Book> results = new TreeSet<>(Comparator.comparingLong(Book::getId));
        results.addAll(searchBookByTitle(keyword));
        results.addAll(searchBookByAuthor(keyword));
        results.addAll(searchBookByIsbn(keyword));
        results.addAll(searchBookByCategory(keyword));

        return results;
    }

}
