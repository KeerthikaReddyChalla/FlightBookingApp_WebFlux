package com.flightapp.util;

import com.flightapp.dto.FlightSearchResponse;
import com.flightapp.model.Airline;
import com.flightapp.model.Inventory;
import com.flightapp.model.TripType;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FlightMapperTest {

    @Test
    void testToSearchResponse() {

        Inventory inv = Inventory.builder()
                .id("INV1")
                .airlineId("A1")
                .fromPlace("Hyd")
                .toPlace("Del")
                .priceOneWay(3000)
                .priceRoundTrip(6000)
                .tripType(TripType.ONE_WAY)
                .availableSeats(50)
                .flightDateTime(LocalDateTime.now())
                .build();

        Airline air = Airline.builder()
                .id("A1")
                .airlineName("Indigo")
                .airlineUrl("logo.png")
                .build();

        FlightSearchResponse resp = FlightMapper.toSearchResponse(inv, air);

        assertEquals("INV1", resp.getFlightId());
        assertEquals("Indigo", resp.getAirlineName());
        assertEquals("Hyd", resp.getFromPlace());
        assertEquals(3000, resp.getPrice());
        assertEquals(50, resp.getAvailableSeats());
    }

    @Test
    void testRoundTripMapping() {

        Inventory inv = Inventory.builder()
                .id("INV2")
                .airlineId("A1")
                .priceOneWay(3000)
                .priceRoundTrip(7000)
                .tripType(TripType.ROUND_TRIP)
                .availableSeats(20)
                .flightDateTime(LocalDateTime.now())
                .build();

        Airline air = Airline.builder()
                .id("A1")
                .airlineName("Air India")
                .build();

        FlightSearchResponse resp = FlightMapper.toSearchResponse(inv, air);

        assertEquals(7000, resp.getPrice());
    }
}
