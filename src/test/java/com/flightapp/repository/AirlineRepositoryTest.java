package com.flightapp.repository;

import com.flightapp.model.Airline;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class AirlineRepositoryTest {

    @Test
    void testFindByAirlineNameIgnoreCase() {

        AirlineRepository repo = Mockito.mock(AirlineRepository.class);

        Airline airline = Airline.builder()
                .id("A1")
                .airlineName("Indigo")
                .active(true)
                .build();

        Mockito.when(repo.findByAirlineNameIgnoreCase("Indigo"))
                .thenReturn(Mono.just(airline));

        Airline result = repo.findByAirlineNameIgnoreCase("Indigo").block();

        assertNotNull(result);
        assertEquals("Indigo", result.getAirlineName());
    }

    @Test
    void testFindByAirlineName_NotFound() {

        AirlineRepository repo = Mockito.mock(AirlineRepository.class);

        Mockito.when(repo.findByAirlineNameIgnoreCase("Unknown"))
                .thenReturn(Mono.empty());

        var result = repo.findByAirlineNameIgnoreCase("Unknown").blockOptional();

        assertTrue(result.isEmpty());
    }
}
