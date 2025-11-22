package com.flightapp.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {

    @NotBlank(message = "Name is required")
    private String userName;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @Positive(message = "Number of seats must be positive")
    private int numberOfSeats;

    @NotNull(message = "Passenger list is required")
    @Size(min = 1, message = "At least one passenger is required")
    private List<PassengerRequest> passengers;
}
