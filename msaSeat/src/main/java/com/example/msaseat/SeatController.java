package com.example.msaseat;

import com.example.msaseat.auth.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seat")
public class SeatController {
    private final SeatService seatService;
    private final TokenProvider tokenProvider;

    private String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSeat(@RequestBody Seat seat, @RequestHeader("Authorization") String bearerToken) {
        String token = resolveToken(bearerToken);
        if (token != null && tokenProvider.validateToken(token)) {
            String authority = tokenProvider.getAuthorityFromToken(token);
            if ("PROVIDER".equals(authority)) {
                return ResponseEntity.ok(seatService.addSeat(seat));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only providers can add seats");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableSeats(@RequestHeader("Authorization") String bearerToken) {
        String token = resolveToken(bearerToken);
        if (token != null && tokenProvider.validateToken(token)) {
            return ResponseEntity.ok(seatService.getAvailableSeats());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

    @PostMapping("/reserve/{seatId}")
    public ResponseEntity<?> reserveSeat(@PathVariable Long seatId, @RequestHeader("Authorization") String bearerToken) {
        String token = resolveToken(bearerToken);
        if (token != null && tokenProvider.validateToken(token)) {
            String userEmail = tokenProvider.getEmailFromToken(token);
            Long userId = tokenProvider.getIdFromToken(token);
            return ResponseEntity.ok(seatService.reserveSeat(seatId, userEmail, userId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

    @PostMapping("/cancel/{seatId}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long seatId, @RequestHeader("Authorization") String bearerToken) {
        String token = resolveToken(bearerToken);
        if (token != null && tokenProvider.validateToken(token)) {
            String userEmail = tokenProvider.getEmailFromToken(token);
            return ResponseEntity.ok(seatService.cancelReservation(seatId, userEmail));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

    @GetMapping("/check/{seatId}")
    public ResponseEntity<?> checkSeatAvailability(@PathVariable Long seatId, @RequestHeader("Authorization") String bearerToken) {
        String token = resolveToken(bearerToken);
        if (token != null && tokenProvider.validateToken(token)) {
            return ResponseEntity.ok(seatService.checkSeatAvailability(seatId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
}
