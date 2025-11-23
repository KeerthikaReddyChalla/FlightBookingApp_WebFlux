package com.flightapp.controller;

import com.flightapp.BaseTestConfig;
import com.flightapp.dto.FlightSearchResponse;
import com.flightapp.dto.SearchFlightRequest;
import com.flightapp.model.TripType;
import com.flightapp.service.FlightSearchService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class FlightSearchControllerTest extends BaseTestConfig {

    @MockBean
    private FlightSearchService flightSearchService;

    @Test
    void testSearchFlightsSuccess() {

        FlightSearchResponse resp = FlightSearchResponse.builder()
                .flightId("F001")
                .airlineName("Indigo")
                .fromPlace("Hyderabad")
                .toPlace("Delhi")
                .price(4500)
                .availableSeats(40)
                .tripType(TripType.ONE_WAY)
                .build();

        Mockito.when(flightSearchService.searchFlights(any()))
                .thenReturn(Flux.just(resp));

        String json = """
                {
                  "fromPlace": "Hyderabad",
                  "toPlace": "Delhi",
                  "flightDateTime": "2025-12-01T00:00:00",
                  "tripType": "ONE_WAY"
                }
                """;

        webTestClient.post()
                .uri("/api/flight/search")
                .contentType(APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].flightId").isEqualTo("F001");
    }

    @Test
    void testSearchFlightsNoResults() {

        Mockito.when(flightSearchService.searchFlights(any()))
                .thenReturn(Flux.empty());

        String json = """
                {
                  "fromPlace": "Hyd",
                  "toPlace": "Del",
                  "flightDateTime": "2025-12-01T00:00:00",
                  "tripType": "ONE_WAY"
                }
                """;

        webTestClient.post()
                .uri("/api/flight/search")
                .contentType(APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("[]");
    }
}
