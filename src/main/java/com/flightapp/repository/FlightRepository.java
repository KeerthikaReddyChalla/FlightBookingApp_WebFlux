package com.flightapp.repository;

import com.flightapp.model.Flight;
import com.flightapp.model.TripType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface FlightRepository extends ReactiveMongoRepository<Flight, String> {

    Flux<Flight> findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndTripType(
            String from,
            String to,
            TripType tripType
    );
}
