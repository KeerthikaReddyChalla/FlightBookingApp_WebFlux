package com.flightapp.dto;

import com.flightapp.model.TripType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddInventoryRequest {

    @NotBlank(message = "Airline ID is required")
    private String airlineId;

    @NotBlank(message = "From place is required")
    private String fromPlace;

    @NotBlank(message = "To place is required")
    private String toPlace;

    @NotNull(message = "Flight date and time is required")
    private LocalDateTime flightDateTime;

    @Positive(message = "Price for one-way must be positive")
    private double priceOneWay;

    @Positive(message = "Price for roundtrip must be positive")
    private double priceRoundTrip;

    @Positive(message = "Total seats must be greater than 0")
    private int totalSeats;

    @NotNull(message = "Trip type is required")
    private TripType tripType;
}
