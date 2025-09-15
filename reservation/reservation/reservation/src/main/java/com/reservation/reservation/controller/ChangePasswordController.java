package com.reservation.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reservation.reservation.ReservationServices.LoginServices;
import com.reservation.reservation.model.Login;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class ChangePasswordController {

    @Autowired
    private LoginServices loginService;

    @GetMapping("/changePassword")
    public String showChangePasswordForm(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUsername");
        
        if (username == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", username);
        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String currentPassword,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                HttpSession session,
                                Model model) {
        
        String username = (String) session.getAttribute("loggedInUsername");
        if (username == null) {
            return "redirect:/login";
        }

        // Check if current password is correct
        Optional<Login> userOpt = loginService.findByUsername(username);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "User not found.");
            return "changePassword";
        }

        Login user = userOpt.get();
        if (!user.getPassword().equals(currentPassword)) {
            model.addAttribute("error", "Current password is incorrect.");
            model.addAttribute("username", username);
            return "changePassword";
        }

        // Check if new passwords match
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New passwords do not match.");
            model.addAttribute("username", username);
            return "changePassword";
        }

        // Check password length
        if (newPassword.length() < 6) {
            model.addAttribute("error", "New password must be at least 6 characters long.");
            model.addAttribute("username", username);
            return "changePassword";
        }

        // Check if new password is same as current
        if (newPassword.equals(currentPassword)) {
            model.addAttribute("error", "New password must be different from current password.");
            model.addAttribute("username", username);
            return "changePassword";
        }

        try {
            // Update the password
            user.setPassword(newPassword);
            loginService.updateUser(user);
            
            model.addAttribute("success", "Password changed successfully!");
            model.addAttribute("username", username);
            return "changePassword";
            
        } catch (Exception e) {
            model.addAttribute("error", "Failed to change password. Please try again.");
            model.addAttribute("username", username);
            return "changePassword";
        }
    }
}
