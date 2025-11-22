package com.flightapp.repository;

import com.flightapp.model.Inventory;
import com.flightapp.model.TripType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface InventoryRepository extends ReactiveMongoRepository<Inventory, String> {

    Flux<Inventory> findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndTripType(
            String from,
            String to,
            TripType tripType
    );

    Flux<Inventory> findByFlightDateTimeBetween(
            LocalDateTime start,
            LocalDateTime end
    );
}
