package com.flightapp.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passenger {

    private String name;
    private Gender gender;
    private int age;
    private String seatNumber;
    private MealType mealType;
}
