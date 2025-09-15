package com.onlineExamination.onlineExamination.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.onlineExamination.onlineExamination.entity.Attempt;
import com.onlineExamination.onlineExamination.entity.Exam;
import com.onlineExamination.onlineExamination.entity.Question;
import com.onlineExamination.onlineExamination.entity.User;
import com.onlineExamination.onlineExamination.repository.AttemptRepository;
import com.onlineExamination.onlineExamination.service.ExamService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.Duration;

@Controller
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private AttemptRepository attemptRepository;
    
    

    @GetMapping("/startExam/{examId}")
    public String startExam(@PathVariable Long examId, Model model) {
        Exam exam = examService.getExamById(examId);
        if (exam == null || exam.getStartTime() == null || exam.getEndTime() == null) {
            return "redirect:/studentDashboard";
        }
		
		  LocalDateTime now = LocalDateTime.now(); if (now.isAfter(exam.getEndTime()))
		  { return "redirect:/studentDashboard"; }
		 
        List<Question> questions = examService.getQuestionsForExam(examId);

        long remainingSeconds = Duration.between(now, exam.getEndTime()).toSeconds();
        model.addAttribute("exam", exam);
        model.addAttribute("examId", examId);
        model.addAttribute("examTitle", exam.getTitle());
        model.addAttribute("questions", questions);
        model.addAttribute("duration", remainingSeconds);

        return "exam"; // exam.jsp
    }

    @PostMapping("/exam/submit/{examId}")
    public String submitExam(@PathVariable Long examId,
                             HttpServletRequest request,
                             HttpSession session,
                             Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Map<Long, String> answers = new HashMap<>();

        // fetch submitted answers
        request.getParameterMap().forEach((key, value) -> {
            if (key.startsWith("q_")) {
                Long qId = Long.parseLong(key.substring(2));
                answers.put(qId, value[0]);
            }
        });

        int score = examService.evaluateExam(examId, answers);

        // Save attempt to database
        Exam exam = examService.getExamById(examId);
        Attempt attempt = new Attempt();
        attempt.setUser(user);
        attempt.setExam(exam);
        attempt.setScore(score);
        attempt.setAttemptTime(LocalDateTime.now());
        attemptRepository.save(attempt);

        model.addAttribute("score", score);
        model.addAttribute("total", answers.size());
        return "result"; // result.jsp
    }
}
























