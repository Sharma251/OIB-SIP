package com.libraryManagement.LibraryManagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libraryManagement.LibraryManagement.entity.Book;
import com.libraryManagement.LibraryManagement.entity.Transaction;
import com.libraryManagement.LibraryManagement.entity.User;
import com.libraryManagement.LibraryManagement.repository.BookRepository;
import com.libraryManagement.LibraryManagement.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    // Issue a book
    public Transaction issueBook(Book book, User user) {
        if (!book.getStatus().equals(Book.Status.AVAILABLE) || book.getQuantity() <= 0) {
            throw new RuntimeException("Book is not available!");
        }

        Transaction transaction = new Transaction();
        transaction.setBook(book);
        transaction.setUser(user);
        transaction.setIssueDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusDays(14)); // 2 weeks due

        book.setStatus(Book.Status.ISSUED);
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        return transactionRepository.save(transaction);
    }

    // Return a book
    public Transaction returnBook(Long transactionId) {
        Optional<Transaction> opt = transactionRepository.findById(transactionId);
        if (opt.isEmpty()) throw new RuntimeException("Transaction not found!");

        Transaction transaction = opt.get();
        transaction.setReturnDate(LocalDate.now());

        if (transaction.getReturnDate().isAfter(transaction.getDueDate())) {
            long daysLate = transaction.getReturnDate().toEpochDay() - transaction.getDueDate().toEpochDay();
            transaction.setFine(daysLate * 50); // ₹50 per day
        }

        // Update book status back to available
        Book book = transaction.getBook();
        book.setStatus(Book.Status.AVAILABLE);
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        return transactionRepository.save(transaction);
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getUserTransactions(User user) {
        return transactionRepository.findByUser(user);
    }

    // Get transaction by ID
    public Optional<Transaction> getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }

    // Issue a book with custom dates
    public Transaction issueBookWithDates(Book book, User user, LocalDate issueDate, LocalDate dueDate) {
        if (!book.getStatus().equals(Book.Status.AVAILABLE) || book.getQuantity() <= 0) {
            throw new RuntimeException("Book is not available!");
        }

        Transaction transaction = new Transaction();
        transaction.setBook(book);
        transaction.setUser(user);
        transaction.setIssueDate(issueDate);
        transaction.setDueDate(dueDate);

        book.setStatus(Book.Status.ISSUED);
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        return transactionRepository.save(transaction);
    }

    // Reserve a book
    public Transaction reserveBook(Book book, User user) {
        if (book.getQuantity() <= 0) {
            throw new RuntimeException("Book is not available for reservation!");
        }

        Transaction transaction = new Transaction();
        transaction.setBook(book);
        transaction.setUser(user);
        // For reservations, issueDate and dueDate are null, returnDate is null
        // This indicates it's a reservation

        book.setStatus(Book.Status.RESERVED);
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        return transactionRepository.save(transaction);
    }

    // Cancel reservation
    public void cancelReservation(Long transactionId) {
        Optional<Transaction> opt = transactionRepository.findById(transactionId);
        if (opt.isEmpty()) throw new RuntimeException("Reservation not found!");

        Transaction transaction = opt.get();

        // Update book status back to available and increase quantity
        Book book = transaction.getBook();
        book.setStatus(Book.Status.AVAILABLE);
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        // Delete the reservation transaction
        transactionRepository.delete(transaction);
    }

    // Issue a reserved book
    public Transaction issueReservedBook(Long transactionId) {
        Optional<Transaction> opt = transactionRepository.findById(transactionId);
        if (opt.isEmpty()) throw new RuntimeException("Reservation not found!");

        Transaction transaction = opt.get();
        Book book = transaction.getBook();

        // Set issue date and due date for the reservation
        transaction.setIssueDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusDays(14)); // 2 weeks due

        // Book status is already RESERVED, quantity already decreased
        // No need to change status or quantity again

        return transactionRepository.save(transaction);
    }

    // Get user reservations (transactions with null issueDate and null returnDate)
    public List<Transaction> getUserReservations(User user) {
        return transactionRepository.findByUserAndIssueDateIsNullAndReturnDateIsNull(user);
    }
}
