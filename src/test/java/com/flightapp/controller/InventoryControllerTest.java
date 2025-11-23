package com.flightapp.controller;

import com.flightapp.BaseTestConfig;
import com.flightapp.TestSecurityConfig;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.Inventory;
import com.flightapp.model.TripType;
import com.flightapp.service.InventoryService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(controllers =InventoryController.class)
@Import(TestSecurityConfig.class)
class InventoryControllerTest extends BaseTestConfig {

    @MockBean
    private InventoryService inventoryService;

    @Test
    void testAddInventorySuccess() {

        Inventory mockInv = Inventory.builder()
                .id("INV001")
                .airlineId("A1")
                .fromPlace("Hyderabad")
                .toPlace("Delhi")
                .tripType(TripType.ONE_WAY)
                .totalSeats(50)
                .availableSeats(50)
                .build();

        Mockito.when(inventoryService.addInventory(any()))
                .thenReturn(Mono.just(mockInv));

        String json = """
                {
                  "airlineId": "A1",
                  "fromPlace": "Hyderabad",
                  "toPlace": "Delhi",
                  "flightDateTime": "2025-12-01T10:30:00",
                  "priceOneWay": 4000,
                  "priceRoundTrip": 7500,
                  "totalSeats": 50,
                  "tripType": "ONE_WAY"
                }
                """;

        webTestClient.post()
                .uri("/api/flight/airline/inventory/add")
                .contentType(APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo("INV001")
                .jsonPath("$.airlineId").isEqualTo("A1");
    }

    @Test
    void testAddInventoryValidationFailure() {

        String json = """
                {
                  "fromPlace": "",
                  "toPlace": "Delhi",
                  "tripType": "ONE_WAY",
                  "totalSeats": 50
                }
                """;

        webTestClient.post()
                .uri("/api/flight/airline/inventory/add")
                .contentType(APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isBadRequest();
    }

   
    @Test
    void testAddInventoryAirlineNotFound() {

        Mockito.when(inventoryService.addInventory(any()))
                .thenThrow(new ResourceNotFoundException("Airline not found"));

        String json = """
        		{
        		  "airlineId": "A1",
        		  "fromPlace": "Hyd",
        		  "toPlace": "Del",
        		  "flightDateTime": "2025-12-01T10:00:00",
        		  "totalSeats": 50,
        		  "priceOneWay": 3000,
        		  "priceRoundTrip": 6000,
        		  "tripType": "ONE_WAY"
        		}
        		""";


        webTestClient.post()
        .uri("/api/flight/airline/inventory/add")
        .contentType(APPLICATION_JSON)
        .bodyValue(json)
        .exchange()
        .expectStatus().isOk() 
        .expectBody()
        .jsonPath("$.status").isEqualTo(404)
        .jsonPath("$.message").isEqualTo("Airline not found");

    }

    }
