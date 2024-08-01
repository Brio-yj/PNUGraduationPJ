package com.example.msaseat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    public List<Seat> getAvailableSeats() {
        return seatRepository.findByIsReservedFalse();
    }
    public Seat reserveSeat(Long seatId, String userEmail, Long userId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        if (seat.isReserved()) {
            throw new RuntimeException("Seat is already reserved");
        }
        seat.setReserved(true);
        seat.setUserEmail(userEmail);
        seat.setUserId(userId);
        return seatRepository.save(seat);
    }
    public Seat cancelReservation(Long seatId, String userEmail) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        if (!seat.isReserved()) {
            throw new RuntimeException("Seat is not reserved");
        }
        if (!seat.getUserEmail().equals(userEmail)) {
            throw new RuntimeException("You are not authorized to cancel this reservation");
        }
        seat.setReserved(false);
        seat.setUserEmail(null);
        seat.setUserId(null);
        return seatRepository.save(seat);
    }
    public Seat addSeat(Seat seat) {
        seat.setSeatType(seat.getSeatType());
        seat.setSeatNumber(seat.getSeatNumber());
        seat.setSeatPrice(seat.getSeatPrice());
        seat.setReserved(false);
        return seatRepository.save(seat);
    }
    public boolean checkSeatAvailability(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        return !seat.isReserved();
    }
}