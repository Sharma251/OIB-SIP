package com.reservation.reservation.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.reservation.reservation.ReservationServices.LoginServices;
import com.reservation.reservation.model.Login;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController 
{
	@GetMapping("/")
	public String dashboard() {
		return "dashboard";  // maps to dashboard.jsp
	}

	
	   private final LoginServices loginService;

	    public DashboardController(LoginServices loginService) {
	        this.loginService = loginService;
	}
	  
	    @GetMapping("/profile")
	    public String profile(HttpSession session, Model model) {
	        String username = (String) session.getAttribute("loggedInUsername");

	        if (username == null) {
	            return "redirect:/login";  // redirect if not logged in
	        }

	        Optional<Login> loginOpt = loginService.findByUsername(username);
	        if (loginOpt.isPresent()) {
	            model.addAttribute("user", loginOpt.get());
	            return "profile"; // must exist: profile.jsp
	        } else {
	            model.addAttribute("error", "User not found");
	            return "error"; // must exist: error.jsp
	        }
	    
	}

}



