package com.onlineExamination.onlineExamination.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onlineExamination.onlineExamination.entity.Exam;
import com.onlineExamination.onlineExamination.entity.Option;
import com.onlineExamination.onlineExamination.entity.Question;
import com.onlineExamination.onlineExamination.entity.User;
import com.onlineExamination.onlineExamination.repository.ExamRepository;
import com.onlineExamination.onlineExamination.repository.OptionRepository;
import com.onlineExamination.onlineExamination.repository.QuestionRepository;
import com.onlineExamination.onlineExamination.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final OptionRepository optionRepository;

    public AdminController(ExamRepository examRepository, UserRepository userRepository, QuestionRepository questionRepository, OptionRepository optionRepository) {
        this.questionRepository = questionRepository;
		this.examRepository = examRepository;
        this.userRepository = userRepository;
        this.optionRepository = optionRepository;
    }

    @GetMapping("/adminDashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "/login";
        }

        // Check if user has ADMIN role
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> "ADMIN".equals(role.getName()));
        if (!isAdmin) {
            return "/studentDashboard";
        }

        List<Exam> exams = examRepository.findAll();
        List<User> users = userRepository.findAll();

        model.addAttribute("exams", exams);
        model.addAttribute("users", users);
        model.addAttribute("user", user);

        return "adminDashboard";
    }
    @GetMapping("/admin/manageQuestions")
    public String manageQuestions(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRoles().stream().anyMatch(role -> "ADMIN".equals(role.getName()))) {
            return "redirect:/login";
        }

        List<Exam> exams = examRepository.findAll();
        model.addAttribute("exams", exams);
        model.addAttribute("user", user);

        return "manageQuestions";
    }

    	
    	/*
		 * User user = (User) session.getAttribute("user"); if (user == null) { return
		 * "redirect:/login"; }
		 * 
		 * // Check if user has ADMIN role boolean isAdmin = user.getRoles().stream()
		 * .anyMatch(role -> "ADMIN".equals(role.getName())); if (!isAdmin) { return
		 * "redirect:/studentDashboard"; }
		 * 
		 * List<Exam> exams = examRepository.findAll(); model.addAttribute("exams",
		 * exams); model.addAttribute("user", user);
		 * 
		 * return "manageQuestions";
		 */

    @GetMapping("/admin/viewResults")
    public String viewResults(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Check if user has ADMIN role
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> "ADMIN".equals(role.getName()));
        if (!isAdmin) {
            return "redirect:/studentDashboard";
        }

        // For now, just return the results view - you can add logic to fetch attempts/results later
        model.addAttribute("user", user);
        return "results";
    }

    // View questions of an exam
    @GetMapping("/questions/{examId}")
    public String viewQuestions(@PathVariable Long examId, Model model, HttpSession session) {
        User admin = (User) session.getAttribute("user");
        if(admin == null || !admin.getRoles().stream().anyMatch(role -> "ADMIN".equals(role.getName()))) {
            return "redirect:/login";
        }

        Exam exam = examRepository.findById(examId).orElse(null);
        if (exam == null) {
            List<Exam> exams = examRepository.findAll();
            model.addAttribute("exams", exams);
            model.addAttribute("errorMessage", "Exam not found!");
            model.addAttribute("user", admin);
            return "manageQuestions";
        }
        model.addAttribute("exam", exam);
        model.addAttribute("questions", questionRepository.findByExam(exam));
        model.addAttribute("user", admin);
        return "manageQuestions";
    }

    // Add Question
    @PostMapping("/questions/add")
    public String addQuestion(@RequestParam Long examId, @RequestParam String text) {
        Exam exam = examRepository.findById(examId).orElse(null);
        if (exam == null) {
            return "redirect:/adminDashboard";
        }
        Question q = new Question();
        q.setText(text);
        q.setExam(exam);
        questionRepository.save(q);
        return "redirect:/questions/" + examId;
    }
    
    
    // Delete Question
    @GetMapping("/questions/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        Question q = questionRepository.findById(id).orElse(null);
        if(q != null) {
            Long examId = q.getExam().getId();
            questionRepository.deleteById(id);
            return "redirect:/questions/" + examId;
        }
        return "redirect:/adminDashboard";
    }

    // Add Option
    @PostMapping("/options/add")
    public String addOption(@RequestParam Long questionId,
                            @RequestParam String text,
                            @RequestParam(required = false) boolean isCorrect) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            return "redirect:/adminDashboard";
        }
        Option option = new Option();
        option.setOptionText(text);
        option.setCorrect(isCorrect);
        option.setQuestion(question);
        optionRepository.save(option);
        return "redirect:/questions/" + question.getExam().getId();
    }
    
    // Delete Option
    @GetMapping("/options/delete/{id}")
    public String deleteOption(@PathVariable Long id) {
        Option opt = optionRepository.findById(id).orElse(null);
        if (opt != null) {
            Long examId = opt.getQuestion().getExam().getId(); // ✅ Use examId, not questionId
            optionRepository.deleteById(id);
            return "redirect:/questions/" + examId;
        }
        return "redirect:/adminDashboard";
    }

    
    
}
