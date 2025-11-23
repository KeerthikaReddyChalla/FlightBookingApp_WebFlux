package com.flightapp.util;

import com.flightapp.dto.BookingResponse;
import com.flightapp.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingMapperTest {

    @Test
    void testToResponse() {

        List<Passenger> passengers = List.of(
                Passenger.builder()
                        .name("A")
                        .gender(Gender.FEMALE)
                        .mealType(MealType.VEG)
                        .seatNumber("1A")
                        .age(22)
                        .build()
        );

        Booking booking = Booking.builder()
                .pnr("PNR001")
                .flightId("F1")
                .userName("Keer")
                .email("k@example.com")
                .bookingDateTime(LocalDateTime.now())
                .journeyDateTime(LocalDateTime.now().plusDays(5))
                .numberOfSeats(1)
                .totalPrice(4000)
                .passengers(passengers)
                .cancelled(false)
                .build();

        BookingResponse resp = BookingMapper.toResponse(booking);

        assertEquals("PNR001", resp.getPnr());
        assertEquals("F1", resp.getFlightId());
        assertEquals(1, resp.getPassengers().size());
        assertFalse(resp.isCancelled());
    }
}
