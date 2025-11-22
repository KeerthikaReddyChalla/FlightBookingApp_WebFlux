package com.flightapp.util;

import com.flightapp.dto.BookingResponse;
import com.flightapp.model.Booking;

public class BookingMapper {

    private BookingMapper() {}

    public static BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .pnr(booking.getPnr())
                .flightId(booking.getFlightId())
                .userName(booking.getUserName())
                .email(booking.getEmail())
                .bookingDateTime(booking.getBookingDateTime())
                .journeyDateTime(booking.getJourneyDateTime())
                .numberOfSeats(booking.getNumberOfSeats())
                .totalPrice(booking.getTotalPrice())
                .passengers(booking.getPassengers())
                .cancelled(booking.isCancelled())
                .build();
    }
}
