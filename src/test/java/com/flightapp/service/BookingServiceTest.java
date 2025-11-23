package com.flightapp.service;

import com.flightapp.dto.BookingRequest;
import com.flightapp.dto.PassengerRequest;
import com.flightapp.dto.CancelResponse;
import com.flightapp.exception.BadRequestException;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.*;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.InventoryRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void testBookTicketSuccess() {

        BookingRequest req = BookingRequest.builder()
                .userName("Keer")
                .email("keer@example.com")
                .numberOfSeats(1)
                .passengers(List.of(
                        PassengerRequest.builder()
                                .name("A")
                                .age(22)
                                .gender(Gender.FEMALE)
                                .mealType(MealType.VEG)
                                .seatNumber("1A")
                                .build()
                ))
                .build();

        Inventory inv = Inventory.builder()
                .id("F001")
                .airlineId("A1")
                .priceOneWay(4000)
                .availableSeats(10)
                .flightDateTime(LocalDateTime.now().plusDays(5))
                .tripType(TripType.ONE_WAY)
                .build();

        Booking saved = Booking.builder()
                .pnr("PNR001")
                .flightId("F001")
                .userName("Keer")
                .email("keer@example.com")
                .journeyDateTime(inv.getFlightDateTime())
                .numberOfSeats(1)
                .totalPrice(4000)
                .passengers(List.of(
                        Passenger.builder().name("A").gender(Gender.FEMALE).seatNumber("1A").mealType(MealType.VEG).age(22).build()
                ))
                .build();

        when(inventoryRepository.findById("F001")).thenReturn(Mono.just(inv));
        when(inventoryRepository.save(any())).thenReturn(Mono.just(inv));
        when(bookingRepository.save(any())).thenReturn(Mono.just(saved));

        Booking result = bookingService.bookTicket("F001", req).block();

        assertNotNull(result);
        assertEquals("PNR001", result.getPnr());
        verify(bookingRepository, times(1)).save(any());
    }

    @Test
    void testBookTicketFlightNotFound() {

        when(inventoryRepository.findById("F001"))
                .thenReturn(Mono.error(new ResourceNotFoundException("Flight not found")));

        BookingRequest req = BookingRequest.builder()
                .numberOfSeats(1)
                .passengers(List.of(
                        PassengerRequest.builder()
                                .name("A")
                                .gender(Gender.FEMALE)
                                .age(22)
                                .seatNumber("1A")
                                .mealType(MealType.VEG)
                                .build()
                ))
                .build();

        assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.bookTicket("F001", req).block();
        });
    }

    @Test
    void testCancelBookingSuccess() {

        Booking booking = Booking.builder()
                .pnr("PNR123")
                .journeyDateTime(LocalDateTime.now().plusDays(3))
                .cancelled(false)
                .build();

        when(bookingRepository.findByPnr("PNR123")).thenReturn(Mono.just(booking));
        when(bookingRepository.save(any())).thenReturn(Mono.just(booking));

        CancelResponse resp = bookingService.cancelBooking("PNR123").block();

        assertEquals("PNR123", resp.getPnr());
        assertEquals("Ticket cancelled successfully", resp.getMessage());
    }

    @Test
    void testCancelBookingTooLate() {

        Booking booking = Booking.builder()
                .pnr("PNR123")
                .journeyDateTime(LocalDateTime.now().plusHours(5))
                .cancelled(false)
                .build();

        when(bookingRepository.findByPnr("PNR123")).thenReturn(Mono.just(booking));

        assertThrows(BadRequestException.class, () -> {
            bookingService.cancelBooking("PNR123").block();
        });
    }
}
