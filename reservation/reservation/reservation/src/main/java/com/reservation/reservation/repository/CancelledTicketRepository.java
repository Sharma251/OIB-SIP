package com.reservation.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservation.reservation.model.CancelledTicket;

public interface CancelledTicketRepository extends JpaRepository<CancelledTicket, Long>
{

}
