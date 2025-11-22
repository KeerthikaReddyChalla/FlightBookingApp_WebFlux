package com.flightapp.dto;

import com.flightapp.model.TripType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchFlightRequest {

    @NotBlank(message = "From place is required")
    private String fromPlace;

    @NotBlank(message = "To place is required")
    private String toPlace;

    @NotNull(message = "Flight date is required")
    private LocalDateTime flightDateTime;

    @NotNull(message = "Trip type is required")
    private TripType tripType;
}
