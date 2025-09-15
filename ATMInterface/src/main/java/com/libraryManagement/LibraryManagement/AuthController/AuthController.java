package com.libraryManagement.LibraryManagement.AuthController;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.libraryManagement.LibraryManagement.entity.User;
import com.libraryManagement.LibraryManagement.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Hardcode server port to 8081 to match application.properties
    private final int serverPort = 8081;

    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.jsp
    }

    @PostMapping("/login")
    public String login(@RequestParam("login") String login,
                        @RequestParam String password,
                        Model model, HttpSession session) {
        System.out.println("Login attempt for user: " + login);
        Optional<User> userOpt = userService.findUserByLogin(login);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                System.out.println("Login successful for user: " + login);
                // Set user in session
                session.setAttribute("user", user);
                if (user.getRole().equals(User.Role.ADMIN)) {
                    return "redirect:/admin/adminDashboard";
                } else {
                    return "redirect:/user/userDashboard";
                }
            } else {
                System.out.println("Password mismatch for user: " + login);
                model.addAttribute("error", "Incorrect password");
            }
        } else {
            System.out.println("No user found for login: " + login);
            model.addAttribute("error", "User not found");
        }
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/forgotPassword")
    public String showForgotPasswordPage() {
        return "forgotPassword"; // JSP
    }

    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("login") String login, Model model) {
        Optional<User> userOpt = userService.findByEmailOrUsername(login);

        if (userOpt.isPresent()) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetToken(userOpt.get(), token);

            // Instead of sending email, just show the reset link on the page for user
            String resetLink = "http://localhost:" + serverPort + "/resetPassword?token=" + token;
            model.addAttribute("message", "Password reset link generated successfully.");
            model.addAttribute("resetLink", resetLink);
        } else {
            model.addAttribute("error", "No user found with that username or email");
        }

        return "forgotPassword";
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        Optional<User> userOpt = userService.findByResetToken(token);

        if (userOpt.isPresent()) {
            model.addAttribute("token", token);
            return "resetPassword"; // JSP page with new password form
        } else {
            model.addAttribute("error", "Invalid or expired token");
            return "forgotPassword";
        }
    }

    @PostMapping("/resetPassword")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            model.addAttribute("token", token);
            return "resetPassword";
        }
        Optional<User> userOpt = userService.findByResetToken(token);
        if (userOpt.isPresent()) {
            userService.updatePassword(userOpt.get(), newPassword);
            model.addAttribute("message", "Password reset successful. You can login now.");
            return "login";
        } else {
            model.addAttribute("error", "Invalid token");
            return "resetPassword";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login"; // login.jsp
    }

}
