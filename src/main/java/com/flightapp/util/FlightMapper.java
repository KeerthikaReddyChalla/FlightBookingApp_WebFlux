package com.flightapp.util;

import com.flightapp.dto.FlightSearchResponse;
import com.flightapp.model.Airline;
import com.flightapp.model.Inventory;

public class FlightMapper {

    private FlightMapper() {}

    public static FlightSearchResponse toSearchResponse(Inventory inventory, Airline airline) {

        double price = inventory.getTripType().name().equals("ONE_WAY")
                ? inventory.getPriceOneWay()
                : inventory.getPriceRoundTrip();

        return FlightSearchResponse.builder()
                .flightId(inventory.getId())
                .airlineName(airline.getAirlineName())
                .airlineLogoUrl(airline.getAirlineLogoUrl())
                .fromPlace(inventory.getFromPlace())
                .toPlace(inventory.getToPlace())
                .flightDateTime(inventory.getFlightDateTime())
                .tripType(inventory.getTripType())
                .price(price)
                .availableSeats(inventory.getAvailableSeats())
                .build();
    }
}
