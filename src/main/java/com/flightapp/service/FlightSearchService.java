package com.flightapp.service;

import com.flightapp.dto.FlightSearchResponse;
import com.flightapp.dto.SearchFlightRequest;
import com.flightapp.repository.AirlineRepository;
import com.flightapp.repository.InventoryRepository;
import com.flightapp.util.FlightMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class FlightSearchService {

    private final InventoryRepository inventoryRepository;
    private final AirlineRepository airlineRepository;

    public Flux<FlightSearchResponse> searchFlights(SearchFlightRequest req) {

        return inventoryRepository
                .findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndTripType(
                        req.getFromPlace(),
                        req.getToPlace(),
                        req.getTripType()
                )
                .filter(inv ->
                        inv.getFlightDateTime().toLocalDate().equals(
                                req.getFlightDateTime().toLocalDate()
                        )
                )
                .flatMap(inv ->
                        airlineRepository.findById(inv.getAirlineId())
                                .map(a -> FlightMapper.toSearchResponse(inv, a))
                );
    }
}
