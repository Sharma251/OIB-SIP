package com.reservation.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reservation.reservation.ReservationServices.BookingServiceImpl;
import com.reservation.reservation.model.TrainDetails;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class DownloadTicketController {

    @Autowired
    private BookingServiceImpl bookingService;

    @GetMapping("/downloadTicket")
    public String showDownloadTicketForm(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUsername");
        
        if (username == null) {
            return "redirect:/login";
        }

        // Get user's booking history for ticket download
        // Note: This would need to be implemented based on your actual data structure
        // For now, we'll show the search form
        model.addAttribute("username", username);
        
        return "downloadTicket";
    }

    @PostMapping("/downloadTicket")
    public String downloadTicket(@RequestParam String pnrNumber, 
                                HttpSession session, 
                                Model model) {
        
        String username = (String) session.getAttribute("loggedInUsername");
        if (username == null) {
            return "redirect:/login";
        }

        // Find booking by PNR number
        Optional<TrainDetails> bookingOpt = bookingService.getByPnr(pnrNumber);
        
        if (bookingOpt.isEmpty()) {
            model.addAttribute("error", "No ticket found with PNR: " + pnrNumber);
            model.addAttribute("username", username);
            return "downloadTicket";
        }

        TrainDetails booking = bookingOpt.get();
        
        // Add booking details to model for ticket display
        model.addAttribute("ticket", booking);
        model.addAttribute("username", username);
        model.addAttribute("success", "Ticket found! You can now download or print your ticket.");
        
        return "downloadTicket";
    }
}
