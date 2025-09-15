package com.reservation.reservation.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reservation.reservation.model.TrainDetails;
import com.reservation.reservation.repository.BookingRepository;
import com.reservation.reservation.repository.BookingService;

@Controller
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	   public BookingController(BookingService bookingService) {
	        this.bookingService = bookingService;
	    }
	
	@Autowired
	private BookingRepository bookingRepository;
	
	
	@GetMapping("/cancelTicket")
	public String cancelTicket(@RequestParam(value = "pnr", required = false) String pnr, Model model) {
	    if (pnr == null || pnr.isEmpty()) {
	        model.addAttribute("errorMessage", "PNR parameter is required to cancel the ticket.");
	        return "cancelTicket"; // or any meaningful page informing the user
	    }

	    Optional<TrainDetails> bookingOpt = bookingRepository.findByPnrNumber(pnr);
	    if (bookingOpt.isEmpty()) {
	        model.addAttribute("errorMessage", "Booking not found for PNR: " + pnr);
	        return "trainSearchForm"; // or any page to show the error
	    }

	    TrainDetails booking = bookingOpt.get();
	    // Proceed with cancellation logic here if needed (e.g., marking status as cancelled)
	    model.addAttribute("booking", booking);
	    return "cancelTicket";
	}

	
	@PostMapping("/cancelTicket")
	public String cancelTicketConfirmed(@RequestParam String pnr, Model model) {
	    Optional<TrainDetails> bookingOpt = bookingRepository.findByPnrNumber(pnr);
	    if (bookingOpt.isEmpty()) {
	        model.addAttribute("errorMessage", "No booking found for PNR: " + pnr);
	        return "bookingSearchForm";
	    }

	    TrainDetails booking = bookingOpt.get();
	    if ("Cancelled".equalsIgnoreCase(booking.getStatus())) {
	        model.addAttribute("infoMessage", "This ticket is already cancelled.");
	    } else {
	        booking.setStatus("Cancelled");
	        bookingRepository.save(booking);
	        model.addAttribute("successMessage", "Ticket cancelled successfully.");
	    }

	    model.addAttribute("booking", booking);
	    return "cancelTicket";
	}

	@GetMapping("/bookingHistory")
	public String showBookingHistory(Model model) {
	    List<TrainDetails> bookings = bookingService.getAllBookings();
	    model.addAttribute("bookings", bookings);
	    return "bookingHistory";
	}


	
	
	 @PostMapping("/confirmCancel")
	    public String confirmCancel(@RequestParam String pnr, Model model) {
	        boolean success = bookingService.cancelBookingByPnr(pnr);

	        if (success) {
	            model.addAttribute("successMsg", "Ticket with PNR " + pnr + " successfully cancelled.");
	        } else {
	            model.addAttribute("errorMsg", "Failed to cancel ticket with PNR " + pnr + ".");
	        }
	        return "cancelTicket";
	    }

	 @GetMapping("/cancelledHistory")
	 public String showCancelledHistory(Model model) {
	     List<TrainDetails> cancelledTickets = bookingService.getCancelledTickets();
	     model.addAttribute("cancelledTickets", cancelledTickets);
	     return "cancelledHistory"; 
	 }

	 
	 //for pnr status
	 @GetMapping("/pnrStatusForm")
	    public String showPnrStatusForm() {
	        return "pnrStatusForm"; // to show PNR input form
	    }

	    @GetMapping("/pnrStatus")
	    public String getPnrStatus(@RequestParam String pnr, Model model) 
	    {
	        Optional<TrainDetails> bookingOpt = bookingService.getByPnr(pnr);

	        if (bookingOpt.isPresent()) 
	        {
	            model.addAttribute("booking", bookingOpt.get());
	        } else {
	            model.addAttribute("error", "PNR number not found.");
	        }
	        return "pnrStatus"; 
	    }
}

