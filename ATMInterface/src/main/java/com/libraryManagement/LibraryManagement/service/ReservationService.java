package com.libraryManagement.LibraryManagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libraryManagement.LibraryManagement.entity.Book;
import com.libraryManagement.LibraryManagement.entity.Reservation;
import com.libraryManagement.LibraryManagement.entity.Transaction;
import com.libraryManagement.LibraryManagement.entity.User;
import com.libraryManagement.LibraryManagement.repository.BookRepository;
import com.libraryManagement.LibraryManagement.repository.ReservationRepository;
import com.libraryManagement.LibraryManagement.repository.TransactionRepository;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Reserve a book
    public Reservation reserveBook(Book book, User user) {
        if (book.getQuantity() <= 0) {
            throw new RuntimeException("Book is not available for reservation!");
        }

        // Check if user already has a pending reservation for this book
        List<Reservation> existingReservations = reservationRepository.findByUserAndBookAndStatus(
            user, book, Reservation.Status.PENDING);
        if (!existingReservations.isEmpty()) {
            throw new RuntimeException("You already have a pending reservation for this book!");
        }

        // Create reservation
        Reservation reservation = new Reservation(book, user);
        reservation.setStatus(Reservation.Status.PENDING);

        // Update book status and quantity
        book.setStatus(Book.Status.RESERVED);
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        return reservationRepository.save(reservation);
    }

    // Cancel reservation
    public void cancelReservation(Long reservationId, User user) {
        Optional<Reservation> opt = reservationRepository.findById(reservationId);
        if (opt.isEmpty()) throw new RuntimeException("Reservation not found!");

        Reservation reservation = opt.get();

        // Verify the reservation belongs to the current user
        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only cancel your own reservations!");
        }

        // Update book status back to available and increase quantity
        Book book = reservation.getBook();
        book.setStatus(Book.Status.AVAILABLE);
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        // Update reservation status
        reservation.setStatus(Reservation.Status.CANCELLED);
        reservationRepository.save(reservation);
    }

    // Issue a reserved book
    public Transaction issueReservedBook(Long reservationId, User user) {
        Optional<Reservation> opt = reservationRepository.findById(reservationId);
        if (opt.isEmpty()) throw new RuntimeException("Reservation not found!");

        Reservation reservation = opt.get();

        // Verify the reservation belongs to the current user
        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only issue your own reservations!");
        }

        Book book = reservation.getBook();

        // Create transaction for issuing the book
        Transaction transaction = new Transaction();
        transaction.setBook(book);
        transaction.setUser(user);
        transaction.setIssueDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusDays(14)); // 2 weeks due

        // Update book status to ISSUED (keep quantity decreased)
        book.setStatus(Book.Status.ISSUED);
        bookRepository.save(book);

        // Update reservation status
        reservation.setStatus(Reservation.Status.ISSUED);
        reservationRepository.save(reservation);

        return transactionRepository.save(transaction);
    }

    // Get user's pending reservations
    public List<Reservation> getUserPendingReservations(User user) {
        return reservationRepository.findByUserAndStatus(user, Reservation.Status.PENDING);
    }

    // Get all reservations for a user
    public List<Reservation> getUserReservations(User user) {
        return reservationRepository.findByUser(user);
    }

    // Get all reservations for a book
    public List<Reservation> getBookReservations(Book book) {
        return reservationRepository.findByBook(book);
    }

    // Check if a user has a pending reservation for a specific book
    public boolean hasPendingReservation(User user, Book book) {
        List<Reservation> reservations = reservationRepository.findByUserAndBookAndStatus(
            user, book, Reservation.Status.PENDING);
        return !reservations.isEmpty();
    }

    // Get reservation by ID
    public Optional<Reservation> getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }
}
