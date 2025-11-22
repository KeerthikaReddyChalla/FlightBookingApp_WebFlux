package com.flightapp.dto;

import com.flightapp.model.Passenger;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private String pnr;
    private String flightId;
    private String userName;
    private String email;

    private LocalDateTime bookingDateTime;
    private LocalDateTime journeyDateTime;

    private int numberOfSeats;
    private double totalPrice;

    private List<Passenger> passengers;
    private boolean cancelled;
}
