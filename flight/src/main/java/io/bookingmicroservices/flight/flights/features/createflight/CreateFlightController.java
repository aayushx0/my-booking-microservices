package io.bookingmicroservices.flight.flights.features.createflight;

import io.bookingmicroservices.flight.flights.InMemoryFlightRepository;
import io.bookingmicroservices.flight.flights.models.Flight;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/flight")
public class CreateFlightController {

    private final InMemoryFlightRepository repository;

    public CreateFlightController(InMemoryFlightRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Flight> create(@Valid @RequestBody CreateFlightRequestDto request) {
        Flight flight = new Flight(
                UUID.randomUUID(),
                request.flightNumber(),
                request.departureAirportCode(),
                request.arrivalAirportCode(),
                request.price());

        repository.save(flight);
        return ResponseEntity.status(HttpStatus.CREATED).body(flight);
    }
}
