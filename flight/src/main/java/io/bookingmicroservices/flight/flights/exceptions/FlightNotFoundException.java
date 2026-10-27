package io.bookingmicroservices.flight.flights.exceptions;

import java.util.UUID;

public class FlightNotFoundException extends RuntimeException {

    public FlightNotFoundException(UUID id) {
        super("Flight with id " + id + " was not found");
    }
}