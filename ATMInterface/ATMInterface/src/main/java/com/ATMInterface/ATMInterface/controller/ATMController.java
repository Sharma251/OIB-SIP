package com.ATMInterface.ATMInterface.controller;

import com.ATMInterface.ATMInterface.model.Transaction;
import com.ATMInterface.ATMInterface.model.User;
import com.ATMInterface.ATMInterface.service.ATMService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ATMController {

    @Autowired
    private ATMService atmService;

    @GetMapping("/")
    public String showHome() {
        return "login";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId, 
                       @RequestParam String pin, 
                       HttpSession session, 
                       Model model) {
        User user = atmService.authenticateUser(userId, pin);
        if (user != null) {
            session.setAttribute("userId", userId);
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid user ID or PIN");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        User user = atmService.getUser(userId);
        model.addAttribute("user", user);
        return "dashboard";
    }

    @GetMapping("/withdraw")
    public String showWithdraw(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        User user = atmService.getUser(userId);
        model.addAttribute("user", user);
        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam double amount, 
                          HttpSession session, 
                          Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        
        User user = atmService.withdraw(userId, amount);
        if (user != null) {
            session.setAttribute("user", user);
            model.addAttribute("success", "Withdrawal successful! New balance: $" + user.getBalance());
        } else {
            model.addAttribute("error", "Withdrawal failed. Insufficient balance or invalid amount.");
        }
        
        model.addAttribute("user", user);
        return "withdraw";
    }

    @GetMapping("/deposit")
    public String showDeposit(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        User user = atmService.getUser(userId);
        model.addAttribute("user", user);
        return "deposit";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam double amount, 
                         HttpSession session, 
                         Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        
        User user = atmService.deposit(userId, amount);
        if (user != null) {
            session.setAttribute("user", user);
            model.addAttribute("success", "Deposit successful! New balance: $" + user.getBalance());
        } else {
            model.addAttribute("error", "Deposit failed. Invalid amount.");
        }
        
        model.addAttribute("user", user);
        return "deposit";
    }

    @GetMapping("/transfer")
    public String showTransfer(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        User user = atmService.getUser(userId);
        model.addAttribute("user", user);
        return "transfer";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam String targetUserId, 
                          @RequestParam double amount, 
                          HttpSession session, 
                          Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        
        User user = atmService.transfer(userId, targetUserId, amount);
        if (user != null) {
            session.setAttribute("user", user);
            model.addAttribute("success", "Transfer successful! New balance: $" + user.getBalance());
        } else {
            model.addAttribute("error", "Transfer failed. Insufficient balance or invalid target user.");
        }
        
        model.addAttribute("user", user);
        return "transfer";
    }

    @GetMapping("/transactions")
    public String showTransactions(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        
        List<Transaction> transactions = atmService.getTransactionHistory(userId);
        User user = atmService.getUser(userId);
        
        model.addAttribute("transactions", transactions);
        model.addAttribute("user", user);
        return "transactions";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
