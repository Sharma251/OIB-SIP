package com.reservation.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.reservation.reservation.model.Login;
import com.reservation.reservation.repository.LoginRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // login.jsp
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {

        Login user = loginRepository.findByUsernameAndPassword(username, password);
        System.out.println("User found? " + (user != null));

        if (user != null) {
            // Set session attribute for user authentication
            session.setAttribute("loggedInUsername", username);
            session.setAttribute("loggedInUser", user);
            model.addAttribute("username", username);
            return "dashboard"; // dashboard.jsp
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // login.jsp again
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Clear session attributes
        session.invalidate();
        return "redirect:/login";
    }
}

