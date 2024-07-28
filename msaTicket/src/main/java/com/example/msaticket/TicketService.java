package com.example.msaticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public List<Ticket> getPurchaseHistory(String email) {
        return ticketRepository.findByUserEmail(email);
    }

    public List<Ticket> getTickets(String email) {
        LocalDate currentDate = LocalDate.now();
        return ticketRepository.findByUserEmail(email).stream()
                .filter(ticket -> ticket.getDate().isAfter(currentDate) || ticket.getDate().isEqual(currentDate))
                .collect(Collectors.toList());
    }

    public Ticket buyTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void cancelReservation(Long ticketId, String email) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("no tickets"));

        if (!ticket.getUserEmail().equals(email)) {
            throw new RuntimeException("no permission");
        }

        ticketRepository.deleteById(ticketId);
    }
}