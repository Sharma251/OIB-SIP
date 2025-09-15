package com.libraryManagement.LibraryManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.libraryManagement.LibraryManagement.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Find user by email (useful for login)
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByName(String name);
    Optional<User> findByResetToken(String resetToken);

    @Query("SELECT u FROM User u WHERE u.email = :login OR u.username = :login")
    Optional<User> findByEmailOrUsername(@Param("login") String login);

    // Check if email exists
    boolean existsByEmail(String email);

}
