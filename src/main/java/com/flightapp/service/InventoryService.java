package com.flightapp.service;

import com.flightapp.dto.AddInventoryRequest;
import com.flightapp.exception.BadRequestException;
import com.flightapp.model.Inventory;
import com.flightapp.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final AirlineService airlineService;

    public Mono<Inventory> addInventory(AddInventoryRequest req) {

        return airlineService.getAirlineById(req.getAirlineId())
                .flatMap(a -> {

                    if (req.getTotalSeats() <= 0)
                        return Mono.error(new BadRequestException("Total seats must be positive"));

                    Inventory inventory = Inventory.builder()
                            .airlineId(req.getAirlineId())
                            .fromPlace(req.getFromPlace())
                            .toPlace(req.getToPlace())
                            .flightDateTime(req.getFlightDateTime())
                            .priceOneWay(req.getPriceOneWay())
                            .priceRoundTrip(req.getPriceRoundTrip())
                            .totalSeats(req.getTotalSeats())
                            .availableSeats(req.getTotalSeats())
                            .tripType(req.getTripType())
                            .build();

                    return inventoryRepository.save(inventory);
                });
    }
}
