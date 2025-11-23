package com.flightapp.controller;

import com.flightapp.BaseTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.flightapp.service.InventoryService;
import com.flightapp.model.Inventory;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class SampleControllerTestTemplate extends BaseTestConfig {

    @MockBean
    private InventoryService inventoryService;

    @Test
    void sampleTest() {
        Inventory inv = Inventory.builder()
                .id("111")
                .airlineId("A1")
                .fromPlace("Hyderabad")
                .toPlace("Delhi")
                .build();

        when(inventoryService.addInventory(org.mockito.ArgumentMatchers.any()))
                .thenReturn(Mono.just(inv));

        webTestClient.post()
                .uri("/api/flight/airline/inventory/add")
                .contentType(APPLICATION_JSON)
                .bodyValue("{\"airlineId\":\"A1\",\"fromPlace\":\"Hyd\",\"toPlace\":\"Del\",\"flightDateTime\":\"2025-12-01T10:30:00\",\"priceOneWay\":100,\"priceRoundTrip\":200,\"totalSeats\":20,\"tripType\":\"ONE_WAY\"}")
                .exchange()
                .expectStatus().isCreated();
    }
}
