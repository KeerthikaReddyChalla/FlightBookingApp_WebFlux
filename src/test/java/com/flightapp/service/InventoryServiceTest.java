package com.flightapp.service;

import com.flightapp.dto.AddInventoryRequest;
import com.flightapp.exception.BadRequestException;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.Airline;
import com.flightapp.model.Inventory;
import com.flightapp.model.TripType;
import com.flightapp.repository.InventoryRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private AirlineService airlineService;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    void testAddInventorySuccess() {

        AddInventoryRequest req = AddInventoryRequest.builder()
                .airlineId("A1")
                .fromPlace("Hyd")
                .toPlace("Del")
                .flightDateTime(java.time.LocalDateTime.now())
                .totalSeats(50)
                .priceOneWay(3000)
                .priceRoundTrip(6000)
                .tripType(TripType.ONE_WAY)
                .build();

        Airline airline = Airline.builder()
                .id("A1")
                .airlineName("Indigo")
                .active(true)
                .build();

        Inventory saved = Inventory.builder()
                .id("INV1")
                .airlineId("A1")
                .fromPlace("Hyd")
                .toPlace("Del")
                .totalSeats(50)
                .availableSeats(50)
                .tripType(TripType.ONE_WAY)
                .build();

        when(airlineService.getAirlineById("A1")).thenReturn(Mono.just(airline));
        when(inventoryRepository.save(any())).thenReturn(Mono.just(saved));

        Inventory result = inventoryService.addInventory(req).block();

        assertNotNull(result);
        assertEquals("INV1", result.getId());
        verify(inventoryRepository, times(1)).save(any());
    }

    @Test
    void testAddInventoryAirlineNotFound() {

        AddInventoryRequest req = AddInventoryRequest.builder()
                .airlineId("A1")
                .totalSeats(50)
                .build();

        when(airlineService.getAirlineById("A1"))
                .thenReturn(Mono.error(new ResourceNotFoundException("Airline not found")));

        assertThrows(ResourceNotFoundException.class, () -> {
            inventoryService.addInventory(req).block();
        });
    }

    @Test
    void testAddInventoryBadSeatCount() {

        AddInventoryRequest req = AddInventoryRequest.builder()
                .airlineId("A1")
                .totalSeats(0)
                .build();

        when(airlineService.getAirlineById("A1"))
                .thenReturn(Mono.just(Airline.builder().id("A1").build()));

        assertThrows(BadRequestException.class, () -> {
            inventoryService.addInventory(req).block();
        });
    }
}
