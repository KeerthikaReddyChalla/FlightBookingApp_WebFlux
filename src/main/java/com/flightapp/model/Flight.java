package com.flightapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "flights")
public class Flight {

    @Id
    private String id;

    private String airlineId;
    private String airlineName;
    private String airlineUrl;
    private String fromPlace;
    private String toPlace;
    private LocalDateTime flightDateTime;
    private TripType tripType;
    private double price;
    private int availableSeats;
}
