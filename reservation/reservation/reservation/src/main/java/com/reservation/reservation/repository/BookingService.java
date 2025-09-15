package com.reservation.reservation.repository;

import java.util.List;
import java.util.Optional;

import com.reservation.reservation.model.TrainDetails;

public interface BookingService {
    List<TrainDetails> getBookingsForUser(Long userId);
    TrainDetails findByPnr(String pnr);
    boolean cancelBookingByPnr(String pnr);
    List<TrainDetails> getAllBookings();
    List<TrainDetails> getCancelledTickets();
    Optional<TrainDetails> getByPnr(String pnr);

}
