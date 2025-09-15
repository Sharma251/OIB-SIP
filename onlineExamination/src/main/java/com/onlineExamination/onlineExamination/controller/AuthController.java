package com.onlineExamination.onlineExamination.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onlineExamination.onlineExamination.entity.User;
import com.onlineExamination.onlineExamination.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
	@Autowired
    private UserRepository userRepository;

    // Show login page
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.jsp
    }
    
    // Handle forgot password form submission
    @PostMapping("/forgotPassword")
    public String handleForgotPassword(@RequestParam String username,
            @RequestParam String newPassword, HttpSession session) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
        	user.setPassword(newPassword);  // ideally hash it!
            userRepository.save(user);
            session.setAttribute("success", "Password updated successfully! Please login.");
            return "login";
        } else {
            session.setAttribute("error", "Invalid username!");
            return "forgotPassword";
        }
    }

    // Map registerStudent POST request to handle form submission
    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute User user, HttpSession session) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            session.setAttribute("error", "Username already exists.");
            return "register";
        }
        userRepository.save(user);
        session.setAttribute("message", "Registration successful. Please login.");
        return "login";
    }

    // Handle login form
    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session) {

        User user = userRepository.findByUsernameAndPassword(username, password);

        if (user != null) {
            session.setAttribute("user", user);

            boolean isAdmin = user.getRoles().stream()
                                  .anyMatch(r -> r.getName().equalsIgnoreCase("ADMIN"));

            if (isAdmin) {
                return "redirect:/adminDashboard";
            } else {
                return "redirect:/studentDashboard";
            }
        } else {
            session.setAttribute("error", "Invalid username or password!");
            return "redirect:/login";
        }
    }
    
   
    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }


    // Forgot password page
    @GetMapping("/forgotPassword")
    public String forgotPasswordPage() {
        return "/forgotPassword"; // forgotPassword.jsp
    }

    // New account page
    @GetMapping("/newAccount")
    public String newAccountPage() {
        return "/newAccount"; // newAccount.jsp
    }
}
