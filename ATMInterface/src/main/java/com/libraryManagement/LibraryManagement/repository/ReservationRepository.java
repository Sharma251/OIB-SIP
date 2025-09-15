package com.libraryManagement.LibraryManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libraryManagement.LibraryManagement.entity.Book;
import com.libraryManagement.LibraryManagement.entity.Reservation;
import com.libraryManagement.LibraryManagement.entity.User;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Find all reservations for a user
    List<Reservation> findByUser(User user);

    // Find all reservations for a book
    List<Reservation> findByBook(Book book);

    // Find active reservations for a user (status = PENDING)
    List<Reservation> findByUserAndStatus(User user, Reservation.Status status);

    // Find reservations for a book with specific status
    List<Reservation> findByBookAndStatus(Book book, Reservation.Status status);

    // Find if a user has already reserved a specific book
    List<Reservation> findByUserAndBookAndStatus(User user, Book book, Reservation.Status status);
}
