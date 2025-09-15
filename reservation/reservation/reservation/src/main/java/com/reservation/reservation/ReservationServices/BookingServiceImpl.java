package com.reservation.reservation.ReservationServices;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.reservation.reservation.model.TrainDetails;
import com.reservation.reservation.repository.BookingRepository;
import com.reservation.reservation.repository.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }


    public List<TrainDetails> getAllBookings() {
        return bookingRepository.findAll();
    }
    
    @Override
    public List<TrainDetails> getBookingsForUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Optional<TrainDetails> getByPnr(String pnr) {
        return bookingRepository.findByPnrNumber(pnr);
    }

    @Override
    public boolean cancelBookingByPnr(String pnrNumber) {
        Optional<TrainDetails> optionalBooking = bookingRepository.findByPnrNumber(pnrNumber);
        if (optionalBooking.isPresent() && !"Cancelled".equalsIgnoreCase(optionalBooking.get().getStatus())) {
            TrainDetails booking = optionalBooking.get();
            booking.setStatus("Cancelled");
            bookingRepository.save(booking);
            return true;
        }
        return false;
    }


	@Override
	public TrainDetails findByPnr(String pnr) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<TrainDetails> getCancelledTickets() {
        return bookingRepository.findByStatus("Cancelled");
    }



}
