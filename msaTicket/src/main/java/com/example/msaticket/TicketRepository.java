package com.example.msaticket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUserEmail(String email);
    List<Ticket> findByUserEmailAndDateGreaterThanEqual(String email, LocalDate date);
}