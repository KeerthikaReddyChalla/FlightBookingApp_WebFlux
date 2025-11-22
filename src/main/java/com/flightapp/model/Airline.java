package com.flightapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "airlines")
public class Airline {

    @Id
    private String id;

    private String airlineName;
    private String airlineUrl;
    private boolean active;
}
