package com.libraryManagement.LibraryManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.libraryManagement.LibraryManagement.entity.Book;
import com.libraryManagement.LibraryManagement.entity.DeletedBook;
import com.libraryManagement.LibraryManagement.entity.User;
import com.libraryManagement.LibraryManagement.entity.Transaction;
import com.libraryManagement.LibraryManagement.repository.BookRepository;
import com.libraryManagement.LibraryManagement.repository.DeletedBookRepository;
import com.libraryManagement.LibraryManagement.service.TransactionService;
import com.libraryManagement.LibraryManagement.service.ReservationService;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private DeletedBookRepository deletedBookRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ReservationService reservationService;

    // Add a new book
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // Update book details
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    // Soft delete book (move to deleted_books table)
    @Transactional
    public void deleteBook(Long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            DeletedBook deletedBook = new DeletedBook();
            deletedBook.setOriginalBookId(book.getId());
            deletedBook.setTitle(book.getTitle());
            deletedBook.setAuthor(book.getAuthor());
            deletedBook.setCategory(book.getCategory());
            deletedBook.setStatus(book.getStatus());
            deletedBook.setCreatedAt(book.getCreatedAt());
            deletedBookRepository.save(deletedBook);
            bookRepository.deleteById(id);
        }
    }

    // Restore book from deleted_books table
    @Transactional
    public void restoreBook(Long deletedBookId) {
        Optional<DeletedBook> deletedBookOpt = deletedBookRepository.findById(deletedBookId);
        if (deletedBookOpt.isPresent()) {
            DeletedBook deletedBook = deletedBookOpt.get();
            Book book = new Book();
            book.setTitle(deletedBook.getTitle());
            book.setAuthor(deletedBook.getAuthor());
            book.setCategory(deletedBook.getCategory());
            book.setStatus(deletedBook.getStatus());
            book.setCreatedAt(deletedBook.getCreatedAt());
            bookRepository.save(book);
            deletedBookRepository.deleteById(deletedBookId);
        }
    }

    // Get all deleted books
    public List<DeletedBook> getAllDeletedBooks() {
        return deletedBookRepository.findAll();
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Find book by ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Search books by title or author
    public List<Book> searchBooks(String query) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query);
    }

    // Get books by category
    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    // Get distinct categories
    public List<String> getDistinctCategories() {
        return bookRepository.findDistinctCategories();
    }

    // Update book status based on quantity
    public void updateBookStatusBasedOnQuantity(Book book) {
        if (book.getQuantity() > 0) {
            book.setStatus(Book.Status.AVAILABLE);
        } else {
            book.setStatus(Book.Status.UNAVAILABLE);
        }
        bookRepository.save(book);
    }

    // Check if book is available for issuing
    public boolean isBookAvailableForIssue(Book book, User user) {
        // Check if quantity > 0 and status is AVAILABLE
        if (book.getQuantity() <= 0 || !book.getStatus().equals(Book.Status.AVAILABLE)) {
            return false;
        }

        // Check if user already has this book issued
        List<Transaction> userTransactions = transactionService.getUserTransactions(user);
        boolean alreadyIssued = userTransactions.stream()
            .anyMatch(t -> t.getBook().getId().equals(book.getId()) &&
                      t.getReturnDate() == null &&
                      t.getIssueDate() != null);

        return !alreadyIssued;
    }

    // Check if book is available for reservation
    public boolean isBookAvailableForReservation(Book book, User user) {
        // Check if quantity > 0
        if (book.getQuantity() <= 0) {
            return false;
        }

        // Check if user already has a pending reservation for this book
        return !reservationService.hasPendingReservation(user, book);
    }

    // Get user's book status (for dashboard display)
    public String getUserBookStatus(Book book, User user) {
        // Check if user has issued this book
        List<Transaction> userTransactions = transactionService.getUserTransactions(user);
        boolean hasIssued = userTransactions.stream()
            .anyMatch(t -> t.getBook().getId().equals(book.getId()) &&
                      t.getReturnDate() == null &&
                      t.getIssueDate() != null);

        if (hasIssued) {
            return "Issued";
        }

        // Check if user has reserved this book
        boolean hasReserved = reservationService.hasPendingReservation(user, book);
        if (hasReserved) {
            return "Reserved";
        }

        // Check if book is available
        if (book.getQuantity() > 0) {
            return "Available";
        } else {
            return "Unavailable";
        }
    }


}
