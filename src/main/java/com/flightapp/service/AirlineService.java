package com.flightapp.service;

import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.Airline;
import com.flightapp.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AirlineService {

    private final AirlineRepository airlineRepository;

    public Mono<Airline> getAirlineById(String id) {
        return airlineRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Airline not found: " + id)));
    }
}
