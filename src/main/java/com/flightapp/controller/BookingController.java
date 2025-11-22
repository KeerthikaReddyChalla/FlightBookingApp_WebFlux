package com.flightapp.controller;

import com.flightapp.dto.BookingRequest;
import com.flightapp.dto.BookingResponse;
import com.flightapp.dto.CancelResponse;
import com.flightapp.model.Booking;
import com.flightapp.service.BookingService;
import com.flightapp.util.BookingMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight")
public class BookingController {

    private final BookingService bookingService;

   
    @PostMapping("/booking/{flightId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookingResponse> bookTicket(
            @PathVariable String flightId,
            @Valid @RequestBody BookingRequest request
    ) {
        return bookingService.bookTicket(flightId, request)
                .map(BookingMapper::toResponse);
    }

    
    @GetMapping("/ticket/{pnr}")
    public Mono<BookingResponse> getTicket(@PathVariable String pnr) {
        return bookingService.getBookingByPnr(pnr)
                .map(BookingMapper::toResponse);
    }

   
    @GetMapping("/booking/history/{emailId}")
    public Mono<List<BookingResponse>> getHistory(@PathVariable String emailId) {
        return bookingService.getHistoryByEmail(emailId)
                .map(list ->
                        list.stream()
                                .map(BookingMapper::toResponse)
                                .toList()
                );
    }

   
    @DeleteMapping("/booking/cancel/{pnr}")
    public Mono<CancelResponse> cancelBooking(@PathVariable String pnr) {
        return bookingService.cancelBooking(pnr);
    }
}
