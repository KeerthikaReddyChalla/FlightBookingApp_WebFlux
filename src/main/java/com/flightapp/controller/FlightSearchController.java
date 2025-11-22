package com.flightapp.controller;

import com.flightapp.dto.SearchFlightRequest;
import com.flightapp.dto.FlightSearchResponse;
import com.flightapp.service.FlightSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight")
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    @PostMapping("/search")
    public Flux<FlightSearchResponse> searchFlights(@Valid @RequestBody SearchFlightRequest request) {
        return flightSearchService.searchFlights(request);
    }
}
