package com.flightapp.repository;

import com.flightapp.model.Inventory;
import com.flightapp.model.TripType;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InventoryRepositoryTest {

    @Test
    void testFindByRoute() {

        InventoryRepository repo = Mockito.mock(InventoryRepository.class);

        Inventory inv = Inventory.builder()
                .id("INV001")
                .fromPlace("Hyd")
                .toPlace("Del")
                .tripType(TripType.ONE_WAY)
                .availableSeats(50)
                .build();

        Mockito.when(repo.findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndTripType(
                "Hyd", "Del", TripType.ONE_WAY))
                .thenReturn(Flux.just(inv));

        Inventory result = repo
                .findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndTripType(
                        "Hyd", "Del", TripType.ONE_WAY)
                .blockFirst();

        assertNotNull(result);
        assertEquals("INV001", result.getId());
    }

    @Test
    void testFindByDateRange() {

        InventoryRepository repo = Mockito.mock(InventoryRepository.class);

        var now = LocalDateTime.now();

        Mockito.when(repo.findByFlightDateTimeBetween(now.minusDays(1), now.plusDays(1)))
                .thenReturn(Flux.empty());

        var list = repo.findByFlightDateTimeBetween(now.minusDays(1), now.plusDays(1))
                .collectList()
                .block();

        assertTrue(list.isEmpty());
    }
}
