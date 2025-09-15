package com.reservation.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reservation.reservation.ReservationServices.LoginServices;
import com.reservation.reservation.model.Login;

import java.util.Optional;

@Controller
public class NewAccountController {

    @Autowired
    private LoginServices loginService;

    @GetMapping("/newAccount")
    public String showNewAccountForm() {
        return "newAccount";
    }

    @PostMapping("/newAccount")
    public String createAccount(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               @RequestParam String fullName,
                               @RequestParam String email,
                               @RequestParam String role,
                               Model model) {
        
        // Check if username already exists
        Optional<Login> existingUser = loginService.findByUsername(username);
        if (existingUser.isPresent()) {
            model.addAttribute("error", "Username already exists. Please choose a different username.");
            model.addAttribute("fullName", fullName);
            model.addAttribute("email", email);
            model.addAttribute("role", role);
            return "newAccount";
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match. Please try again.");
            model.addAttribute("username", username);
            model.addAttribute("fullName", fullName);
            model.addAttribute("email", email);
            model.addAttribute("role", role);
            return "newAccount";
        }

        // Check password length
        if (password.length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters long.");
            model.addAttribute("username", username);
            model.addAttribute("fullName", fullName);
            model.addAttribute("email", email);
            model.addAttribute("role", role);
            return "newAccount";
        }

        // Check if required fields are not empty
        if (username.trim().isEmpty() || fullName.trim().isEmpty() || email.trim().isEmpty()) {
            model.addAttribute("error", "All fields are required.");
            model.addAttribute("username", username);
            model.addAttribute("fullName", fullName);
            model.addAttribute("email", email);
            model.addAttribute("role", role);
            return "newAccount";
        }

        try {
            // Create new user
            Login newUser = new Login();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setFullName(fullName);
            newUser.setEmail(email);
            newUser.setRole(role != null && !role.trim().isEmpty() ? role : "USER");

            // Save the new user
            loginService.createUser(newUser);
            
            model.addAttribute("success", "Account created successfully! You can now login with your username and password.");
            return "newAccount";
            
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create account. Please try again.");
            model.addAttribute("username", username);
            model.addAttribute("fullName", fullName);
            model.addAttribute("email", email);
            model.addAttribute("role", role);
            return "newAccount";
        }
    }
}
