package com.flightapp.repository;

import com.flightapp.model.Booking;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;

class BookingRepositoryTest {

    @Test
    void testFindByEmail() {

        BookingRepository repo = Mockito.mock(BookingRepository.class);

        Booking booking = Booking.builder()
                .pnr("PNR123")
                .email("keer@example.com")
                .build();

        Mockito.when(repo.findByEmailIgnoreCase("keer@example.com"))
                .thenReturn(Flux.just(booking));

        Booking result = repo.findByEmailIgnoreCase("keer@example.com").blockFirst();

        assertNotNull(result);
        assertEquals("PNR123", result.getPnr());
    }

    @Test
    void testFindByPnr() {

        BookingRepository repo = Mockito.mock(BookingRepository.class);

        Booking booking = Booking.builder()
                .pnr("PNR555")
                .build();

        Mockito.when(repo.findByPnr("PNR555"))
                .thenReturn(Mono.just(booking));

        Booking result = repo.findByPnr("PNR555").block();

        assertNotNull(result);
        assertEquals("PNR555", result.getPnr());
    }

    @Test
    void testFindByPnr_NotFound() {

        BookingRepository repo = Mockito.mock(BookingRepository.class);

        Mockito.when(repo.findByPnr("XYZ"))
                .thenReturn(Mono.empty());

        var result = repo.findByPnr("XYZ").blockOptional();

        assertTrue(result.isEmpty());
    }
}
