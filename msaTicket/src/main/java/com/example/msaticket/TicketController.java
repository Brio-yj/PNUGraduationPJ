package com.example.msaticket;

import com.example.msaticket.auth.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final TokenProvider tokenProvider;

    @GetMapping("/history")
    public ResponseEntity<List<Ticket>> getPurchaseHistory(@RequestHeader("Authorization") String bearerToken) {
        String token = resolveToken(bearerToken);
        String email = tokenProvider.getEmailFromToken(token);
        return ResponseEntity.ok(ticketService.getPurchaseHistory(email));
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getTickets(@RequestHeader("Authorization") String bearerToken) {
        String token = resolveToken(bearerToken);
        String email = tokenProvider.getEmailFromToken(token);
        return ResponseEntity.ok(ticketService.getTickets(email));
    }

    @PostMapping
    public ResponseEntity<Ticket> buyTicket(@RequestBody Ticket ticket, @RequestHeader("Authorization") String bearerToken) {
        String token = resolveToken(bearerToken);
        Long userId = tokenProvider.getMemberIdFromToken(token);
        String email = tokenProvider.getEmailFromToken(token);
        ticket.setUserId(userId);
        ticket.setUserEmail(email);
        return ResponseEntity.ok(ticketService.buyTicket(ticket));
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long ticketId, @RequestHeader("Authorization") String bearerToken) {
        String token = resolveToken(bearerToken);
        String email = tokenProvider.getEmailFromToken(token);
        ticketService.cancelReservation(ticketId, email);
        return ResponseEntity.ok().build();
    }

    private String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}