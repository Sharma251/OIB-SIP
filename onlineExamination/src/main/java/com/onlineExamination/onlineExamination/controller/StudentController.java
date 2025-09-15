package com.onlineExamination.onlineExamination.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onlineExamination.onlineExamination.entity.Attempt;
import com.onlineExamination.onlineExamination.entity.Exam;
import com.onlineExamination.onlineExamination.entity.User;
import com.onlineExamination.onlineExamination.repository.AttemptRepository;
import com.onlineExamination.onlineExamination.repository.ExamRepository;
import com.onlineExamination.onlineExamination.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentController {

    private final ExamRepository examRepository;
    private final AttemptRepository attemptRepository;
    private final UserRepository userRepository;

    public StudentController(ExamRepository examRepository, AttemptRepository attemptRepository, UserRepository userRepository) {
        this.examRepository = examRepository;
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/studentDashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        List<Exam> exams = examRepository.findAll();
        List<Attempt> attempts = attemptRepository.findByUser(user);

        model.addAttribute("exams", exams);
        model.addAttribute("attempts", attempts);
        model.addAttribute("user", user);

        return "studentDashboard";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@RequestParam String fullName, @RequestParam String email, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        user.setFullName(fullName);
        user.setEmail(email);
        userRepository.save(user);
        model.addAttribute("success", "Profile updated successfully");
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String currentPassword, @RequestParam String newPassword, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        if (!user.getPassword().equals(currentPassword)) {
            model.addAttribute("error", "Current password is incorrect");
            model.addAttribute("user", user);
            return "profile";
        }
        user.setPassword(newPassword);
        userRepository.save(user);
        model.addAttribute("success", "Password changed successfully");
        model.addAttribute("user", user);
        return "profile";
    }
}