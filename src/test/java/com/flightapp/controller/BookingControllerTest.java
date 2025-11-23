package com.flightapp.controller;

import com.flightapp.BaseTestConfig;
import com.flightapp.dto.BookingRequest;
import com.flightapp.dto.CancelResponse;
import com.flightapp.model.*;
import com.flightapp.service.BookingService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class BookingControllerTest extends BaseTestConfig {

    @MockBean
    private BookingService bookingService;

    @Test
    void testBookTicketSuccess() {

        Booking booking = Booking.builder()
                .pnr("PNR12345")
                .flightId("F001")
                .userName("Keerthika")
                .email("keer@example.com")
                .bookingDateTime(LocalDateTime.now())
                .journeyDateTime(LocalDateTime.now().plusDays(5))
                .numberOfSeats(2)
                .totalPrice(8000)
                .passengers(List.of(
                        Passenger.builder().name("A").age(22).gender(Gender.FEMALE).seatNumber("1A").mealType(MealType.VEG).build()
                ))
                .cancelled(false)
                .build();

        Mockito.when(bookingService.bookTicket(any(), any()))
                .thenReturn(Mono.just(booking));

        String json = """
                {
                  "userName": "Keerthika",
                  "email": "keer@example.com",
                  "numberOfSeats": 1,
                  "passengers": [
                    {
                      "name": "A",
                      "gender": "FEMALE",
                      "age": 22,
                      "seatNumber": "1A",
                      "mealType": "VEG"
                    }
                  ]
                }
                """;

        webTestClient.post()
                .uri("/api/flight/booking/F001")
                .contentType(APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.pnr").isEqualTo("PNR12345");
    }

    @Test
    void testGetTicketByPnr() {

        Booking booking = Booking.builder()
                .pnr("PNR12345")
                .flightId("F001")
                .userName("Keerthika")
                .email("k@example.com")
                .bookingDateTime(LocalDateTime.now())
                .journeyDateTime(LocalDateTime.now().plusDays(10))
                .cancelled(false)
                .build();

        Mockito.when(bookingService.getBookingByPnr("PNR12345"))
                .thenReturn(Mono.just(booking));

        webTestClient.get()
                .uri("/api/flight/ticket/PNR12345")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.pnr").isEqualTo("PNR12345");
    }

    @Test
    void testCancelTicket() {

        CancelResponse resp = CancelResponse.builder()
                .pnr("PNR12345")
                .message("Ticket cancelled successfully")
                .build();

        Mockito.when(bookingService.cancelBooking("PNR12345"))
                .thenReturn(Mono.just(resp));

        webTestClient.delete()
                .uri("/api/flight/booking/cancel/PNR12345")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Ticket cancelled successfully");
    }
}
