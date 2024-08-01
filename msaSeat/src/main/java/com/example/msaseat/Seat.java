package com.example.msaseat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String seatType;

    @Column(nullable = false)
    private String seatNumber;

    @Column(nullable = false)
    private String seatPrice;

    @Column
    private String userEmail;

    @Column
    private Long userId;

    @Column(nullable = false)
    private boolean isReserved = false;
}