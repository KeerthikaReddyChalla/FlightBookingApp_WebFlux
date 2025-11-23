package com.flightapp.dto;

import com.flightapp.model.Gender;
import com.flightapp.model.MealType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {

    @NotBlank(message = "User name is required")
    private String userName;

    @Email(message = "Email is invalid")
    @NotBlank(message = "Email is required")
    private String email;

    @Min(value = 1, message = "At least 1 seat must be booked")
    private int numberOfSeats;

    @Valid
    @NotNull(message = "Passenger list cannot be empty")
    private List<PassengerRequest> passengers;
}
