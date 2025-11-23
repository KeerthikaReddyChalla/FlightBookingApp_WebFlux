package com.flightapp.service;

import com.flightapp.dto.FlightSearchResponse;
import com.flightapp.dto.SearchFlightRequest;
import com.flightapp.model.Airline;
import com.flightapp.model.Inventory;
import com.flightapp.model.TripType;
import com.flightapp.repository.AirlineRepository;
import com.flightapp.repository.InventoryRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightSearchServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private AirlineRepository airlineRepository;

    @InjectMocks
    private FlightSearchService flightSearchService;

    @Test
    void testSearchFlights() {

        SearchFlightRequest req = SearchFlightRequest.builder()
                .fromPlace("Hyd")
                .toPlace("Del")
                .flightDateTime(LocalDateTime.of(2025, 12, 1, 0, 0))
                .tripType(TripType.ONE_WAY)
                .build();

        Inventory inv = Inventory.builder()
                .id("INV1")
                .airlineId("A1")
                .fromPlace("Hyd")
                .toPlace("Del")
                .flightDateTime(LocalDateTime.of(2025, 12, 1, 10, 30))
                .priceOneWay(4500)
                .availableSeats(40)
                .tripType(TripType.ONE_WAY)
                .build();

        Airline air = Airline.builder()
                .id("A1")
                .airlineName("Indigo")
                .airlineUrl("logo")
                .build();

        when(inventoryRepository
                .findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndTripType("Hyd","Del",TripType.ONE_WAY))
                .thenReturn(Flux.just(inv));

        when(airlineRepository.findById("A1")).thenReturn(Mono.just(air));

        FlightSearchResponse resp = flightSearchService.searchFlights(req).blockFirst();

        assertNotNull(resp);
        assertEquals("INV1", resp.getFlightId());
        assertEquals("Indigo", resp.getAirlineName());
    }

    @Test
    void testSearchFlightsEmpty() {

        when(inventoryRepository
                .findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndTripType(any(),any(),any()))
                .thenReturn(Flux.empty());

        SearchFlightRequest req = SearchFlightRequest.builder()
                .fromPlace("X")
                .toPlace("Y")
                .flightDateTime(LocalDateTime.now())
                .tripType(TripType.ONE_WAY)
                .build();

        var flux = flightSearchService.searchFlights(req);

        assertEquals(0, flux.collectList().block().size());
    }
}
