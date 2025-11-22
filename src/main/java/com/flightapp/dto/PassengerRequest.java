package com.flightapp.dto;

import com.flightapp.model.Gender;
import com.flightapp.model.MealType;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerRequest {

    @NotBlank(message = "Passenger name is required")
    private String name;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @Positive(message = "Age must be greater than 0")
    private int age;

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    @NotNull(message = "Meal type is required")
    private MealType mealType;
}
