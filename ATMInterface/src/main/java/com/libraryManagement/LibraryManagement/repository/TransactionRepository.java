package com.libraryManagement.LibraryManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libraryManagement.LibraryManagement.entity.Book;
import com.libraryManagement.LibraryManagement.entity.Transaction;
import com.libraryManagement.LibraryManagement.entity.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Find all transactions of a user
    List<Transaction> findByUser(User user);

    // Find all transactions of a book
    List<Transaction> findByBook(Book book);

    // Find active (not returned) transactions for a user
    List<Transaction> findByUserAndReturnDateIsNull(User user);

    // Find reservations (issueDate and returnDate are null) for a user
    List<Transaction> findByUserAndIssueDateIsNullAndReturnDateIsNull(User user);
}
