package com.flightapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "inventories")
public class Inventory {

    @Id
    private String id;

    private String airlineId;
    private String fromPlace;
    private String toPlace;
    private LocalDateTime flightDateTime;
    private double priceOneWay;
    private double priceRoundTrip;
    private int totalSeats;
    private int availableSeats;
    private TripType tripType;
}
