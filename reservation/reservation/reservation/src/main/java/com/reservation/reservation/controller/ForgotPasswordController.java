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
public class ForgotPasswordController {

    @Autowired
    private LoginServices loginService;

    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String resetPassword(@RequestParam String username,
                               @RequestParam String newPassword,
                               @RequestParam String confirmPassword,
                               Model model) {
        
        // Check if username exists
        Optional<Login> userOpt = loginService.findByUsername(username);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "Username not found. Please check and try again.");
            return "forgotPassword";
        }

        // Check if passwords match
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match. Please try again.");
            model.addAttribute("username", username);
            return "forgotPassword";
        }

        // Check password length
        if (newPassword.length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters long.");
            model.addAttribute("username", username);
            return "forgotPassword";
        }

        try {
            // Update the password
            Login user = userOpt.get();
            user.setPassword(newPassword);
            loginService.updateUser(user);
            
            model.addAttribute("success", "Password reset successfully! You can now login with your new password.");
            return "forgotPassword";
            
        } catch (Exception e) {
            model.addAttribute("error", "Failed to reset password. Please try again.");
            model.addAttribute("username", username);
            return "forgotPassword";
        }
    }
}
