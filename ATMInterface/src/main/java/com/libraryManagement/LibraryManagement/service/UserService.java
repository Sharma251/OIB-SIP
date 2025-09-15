package com.libraryManagement.LibraryManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.libraryManagement.LibraryManagement.entity.User;
import com.libraryManagement.LibraryManagement.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register a new user
    @Transactional
    public User registerUser(User user) {
        // Since email is no longer used for login, skip email uniqueness check
        return userRepository.save(user);
    }

    // Find user by username or email
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findByEmailOrUsername(login);
    }

    // Login by username or email + password
    public Optional<User> login(String login, String password) {
        System.out.println("UserService.login called with login: " + login);
        Optional<User> user = findUserByLogin(login);
        if (user.isPresent()) {
            System.out.println("User found: " + user.get().getEmail());
            if (user.get().getPassword().equals(password)) {
                System.out.println("Password match for user: " + login);
                return user;
            } else {
                System.out.println("Password mismatch for user: " + login);
            }
        } else {
            System.out.println("No user found for login: " + login);
        }
        return Optional.empty();
    }

    // Get all users (Admin use only)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Find user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> findByEmailOrUsername(String login) {
        return userRepository.findByEmailOrUsername(login);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void createPasswordResetToken(User user, String token) {
        user.setResetToken(token);
        userRepository.save(user);
    }

    public Optional<User> findByResetToken(String token) {
        return userRepository.findByResetToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        // ⚠️ Ideally encode with BCrypt
        user.setPassword(newPassword);
        user.setResetToken(null); // clear token after use
        userRepository.save(user);
    }
}
