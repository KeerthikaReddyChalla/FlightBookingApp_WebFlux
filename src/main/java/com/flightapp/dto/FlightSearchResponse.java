package com.flightapp.dto;

import com.flightapp.model.TripType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSearchResponse {

    private String flightId;
    private String airlineName;
    private String airlineUrl;

    private String fromPlace;
    private String toPlace;

    private LocalDateTime flightDateTime;
    private TripType tripType;

    private double price;
    private int availableSeats;
}
