package com.example.msaeventinformation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private String cast;

    @Column
    private String description;

    private String venue;

    @Column
    private String seatsAndPrices;

    private LocalDateTime eventTime;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime bookingStartDate;

    private LocalDateTime bookingEndDate;


    private Long memberId;
    private String memberEmail;

}