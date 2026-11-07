package io.bookingmicroservices.flight.flights.features.updateflight;

import io.bookingmicroservices.flight.data.jpa.entities.FlightEntity;
import io.bookingmicroservices.flight.data.jpa.repositories.FlightJpaRepository;
import io.bookingmicroservices.flight.flights.exceptions.FlightNotFoundException;
import io.bookingmicroservices.flight.flights.models.Flight;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/flight")
public class UpdateFlightController {

    private final FlightJpaRepository repository;

    public UpdateFlightController(FlightJpaRepository repository) {
        this.repository = repository;
    }

    @PutMapping("/{id}")
    public Flight update(@PathVariable UUID id, @Valid @RequestBody UpdateFlightRequestDto request) {
        if (!repository.existsById(id)) {
            throw new FlightNotFoundException(id);
        }

        Flight flight = new Flight(
                id,
                request.flightNumber(),
                request.departureAirportCode(),
                request.arrivalAirportCode(),
                request.price());

        repository.save(FlightEntity.from(flight));
        return flight;
    }
}