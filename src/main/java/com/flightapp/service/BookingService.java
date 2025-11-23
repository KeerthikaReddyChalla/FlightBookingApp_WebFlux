package com.flightapp.service;

import com.flightapp.dto.BookingRequest;
import com.flightapp.dto.CancelResponse;
import com.flightapp.exception.BadRequestException;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.*;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.InventoryRepository;
import com.flightapp.util.PNRGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final InventoryRepository inventoryRepository;

   
    public Mono<Booking> bookTicket(String flightId, BookingRequest req) {

        return inventoryRepository.findById(flightId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Flight not found")))
                .flatMap(inventory -> {

                    if (inventory.getAvailableSeats() < req.getNumberOfSeats()) {
                        return Mono.error(new BadRequestException("Not enough seats available"));
                    }

                    double price = inventory.getTripType() == TripType.ONE_WAY ?
                            inventory.getPriceOneWay() :
                            inventory.getPriceRoundTrip();

                    Booking booking = Booking.builder()
                            .pnr(PNRGenerator.generatePNR())
                            .flightId(flightId)
                            .userName(req.getUserName())
                            .email(req.getEmail())
                            .bookingDateTime(LocalDateTime.now())
                            .journeyDateTime(inventory.getFlightDateTime())
                            .numberOfSeats(req.getNumberOfSeats())
                            .totalPrice(price * req.getNumberOfSeats())
                            .cancelled(false)
                            .passengers(req.getPassengers().stream().map(p ->
                                    Passenger.builder()
                                            .name(p.getName())
                                            .age(p.getAge())
                                            .gender(p.getGender())
                                            .mealType(p.getMealType())
                                            .seatNumber(p.getSeatNumber())
                                            .build()
                            ).collect(Collectors.toList()))
                            .build();

                    inventory.setAvailableSeats(inventory.getAvailableSeats() - req.getNumberOfSeats());

                    return inventoryRepository.save(inventory)
                            .then(bookingRepository.save(booking));
                });
    }

    public Mono<Booking> getBookingByPnr(String pnr) {
        return bookingRepository.findByPnr(pnr)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("PNR not found")));
    }

   
    public Mono<java.util.List<Booking>> getHistoryByEmail(String email) {
        return bookingRepository.findByEmailIgnoreCase(email).collectList();
    }

    
    public Mono<CancelResponse> cancelBooking(String pnr) {

        return bookingRepository.findByPnr(pnr)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Invalid PNR")))
                .flatMap(booking -> {

                    if (booking.isCancelled()) {
                        return Mono.error(new BadRequestException("Ticket already cancelled"));
                    }

                    if (booking.getJourneyDateTime().minusHours(24).isBefore(LocalDateTime.now())) {
                        return Mono.error(new BadRequestException("Cancellation not allowed within 24 hours"));
                    }

                    booking.setCancelled(true);

                    return bookingRepository.save(booking)
                            .thenReturn(
                                    CancelResponse.builder()
                                            .pnr(pnr)
                                            .message("Ticket cancelled successfully")
                                            .build()
                            );
                });
    }
}
