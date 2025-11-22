package com.flightapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings")
public class Booking {

    @Id
    private String pnr;

    private String flightId;
    private String userName;
    private String email;
    private LocalDateTime bookingDateTime;
    private int numberOfSeats;
    private List<Passenger> passengers;
    private double totalPrice;
    private boolean cancelled;
    private LocalDateTime journeyDateTime;
}
