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
public class EditProfileController {

    @Autowired
    private LoginServices loginService;

    @GetMapping("/editProfile")
    public String showEditProfileForm(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUsername");
        
        if (username == null) {
            return "redirect:/login";
        }

        Optional<Login> userOpt = loginService.findByUsername(username);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "editProfile";
        } else {
            model.addAttribute("error", "User not found");
            return "redirect:/profile";
        }
    }

    @PostMapping("/editProfile")
    public String updateProfile(@RequestParam String fullName,
                               @RequestParam String email,
                               HttpSession session,
                               Model model) {
        String username = (String) session.getAttribute("loggedInUsername");
        
        if (username == null) {
            return "redirect:/login";
        }

        Optional<Login> userOpt = loginService.findByUsername(username);
        if (userOpt.isPresent()) {
            Login user = userOpt.get();
            user.setFullName(fullName);
            user.setEmail(email);
            
            // Save the updated user
            try {
                loginService.updateUser(user);
                model.addAttribute("success", "Profile updated successfully!");
                model.addAttribute("user", user);
                return "editProfile";
            } catch (Exception e) {
                model.addAttribute("error", "Failed to update profile: " + e.getMessage());
                model.addAttribute("user", user);
                return "editProfile";
            }
        } else {
            model.addAttribute("error", "User not found");
            return "redirect:/profile";
        }
    }
}
