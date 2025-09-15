package com.reservation.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.databind.JsonNode;
import com.reservation.reservation.ReservationServices.RapidApiAsyncService;
import com.reservation.reservation.model.TrainDetails;
import com.reservation.reservation.repository.BookingRepository;

@Controller
public class TrainController {
	@Autowired
    private RapidApiAsyncService rapidApiAsyncService;

	@GetMapping("/trainStatus")
	public String showTrainStatus(@RequestParam String trainNumber,
	                               @RequestParam String date,
	                               Model model) {
	    JsonNode status = rapidApiAsyncService.getParsedTrainStatus(trainNumber, date);
	    model.addAttribute("trainInfo", status);
	    return "trainStatus";
	}

	@GetMapping("/trainSearchForm")
	public String searchTrain(@RequestParam(required = false) String trainNumber, Model model) {
	    if (trainNumber == null || trainNumber.trim().isEmpty()) {
	        model.addAttribute("errorMessage", "Please enter a train number or name.");
	        return "trainSearchForm";  // Show the search form again with error
	    }
	    
	    List<TrainDetails> trains = rapidApiAsyncService.searchAndParseTrains(trainNumber);
	    model.addAttribute("trains", trains);
	    return "trainList"; // Display results page
	}

	@Autowired
	private BookingRepository bookingRepository;
	@GetMapping("/bookTicket/{trainNumber}")
	public String showBookTicketForm(@PathVariable String trainNumber,
	                                 @RequestParam(required = false) String trainName,
	                                 @RequestParam(required = false) String origin,
	                                 @RequestParam(required = false) String destination,
	                                 Model model) {
	    // If parameters are not provided, fetch details from API
	    if (trainName == null || origin == null || destination == null) {
	        TrainDetails details = rapidApiAsyncService.getTrainDetails(trainNumber);
	        if (details != null) {
	            trainName = details.getTrainName();
	            origin = details.getOrigin();
	            destination = details.getDestination();
	        } else {
	            model.addAttribute("errorMessage", "Train not found.");
	            return "trainSearchForm";
	        }
	    }

	    model.addAttribute("trainNumber", trainNumber);
	    model.addAttribute("trainName", trainName);
	    model.addAttribute("origin", origin);
	    model.addAttribute("destination", destination);

	    return "bookTicket"; // Will forward to /WEB-INF/bookTicket.jsp
	}
	
	
	@PostMapping("/bookTicket")
	public String processTrainDetails(
	        @RequestParam String trainNumber,
	        @RequestParam String trainName,
	        @RequestParam String origin,
	        @RequestParam String destination,
	        @RequestParam String passengerName,
	        @RequestParam int passengerAge,
	        @RequestParam String gender,
	        @RequestParam String journeyDate,
	        @RequestParam String classType,
	        @RequestParam(required = false) String seatPreference,
	        Model model) {

	    // Ideally, Booking should be a separate entity.
	    // For demo, create and populate a Booking object or map data as needed.
	    // Example assuming you have a Booking class:
	    TrainDetails booking = new TrainDetails();
	    booking.settrainNumber(trainNumber);
	    booking.setTrainName(trainName);
	    booking.setOrigin(origin);
	    booking.setDestination(destination);
	    booking.setPassengerName(passengerName);
	    booking.setPassengerAge(passengerAge);
	    booking.setGender(gender);
	    booking.setJourneyDate(journeyDate);
	    booking.setClassType(classType);
	    booking.setSeatPreference(seatPreference);

	    String generatedPNR = "PNR" + System.currentTimeMillis();
	    booking.setPnrNumber(generatedPNR);
	    // Save booking to database via repository
	    bookingRepository.save(booking);

	    // Add booking and PNR to model for JSP rendering
	    model.addAttribute("booking", booking);
	    model.addAttribute("pnr", generatedPNR);

	    // Return booking confirmation view
	    return "bookingConfirmation";
	}

}
