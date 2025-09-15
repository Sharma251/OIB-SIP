package com.reservation.reservation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservation.reservation.model.TrainDetails;

public interface BookingRepository extends JpaRepository<TrainDetails, Long> {
    List<TrainDetails> findByUserId(Long userId); 
    Optional <TrainDetails> findByPnrNumber(String pnrNumber);
    List<TrainDetails> findByUserIdAndStatus(Long userId, String status);
    List<TrainDetails> findByStatus(String status);
}
