package io.bookingmicroservices.flight.flights.models;

import java.math.BigDecimal;
import java.util.UUID;

public record Flight(
        UUID id,
        String flightNumber,
        String departureAirportCode,
        String arrivalAirportCode,
        BigDecimal price) {
}