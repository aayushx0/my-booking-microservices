package io.bookingmicroservices.flight.flights.features.createflight;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateFlightRequestDto(
        @NotBlank @Size(max = 10) String flightNumber,
        @NotBlank @Size(min = 3, max = 3) String departureAirportCode,
        @NotBlank @Size(min = 3, max = 3) String arrivalAirportCode,
        @NotNull @Positive BigDecimal price) {
}