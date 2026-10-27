package io.bookingmicroservices.flight.flights.features.getflightbyid;

import io.bookingmicroservices.flight.data.jpa.entities.FlightEntity;
import io.bookingmicroservices.flight.data.jpa.repositories.FlightJpaRepository;
import io.bookingmicroservices.flight.flights.exceptions.FlightNotFoundException;
import io.bookingmicroservices.flight.flights.models.Flight;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/flight")
public class GetFlightByIdController {

    private final FlightJpaRepository repository;

    public GetFlightByIdController(FlightJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public Flight getById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(FlightEntity::toModel)
                .orElseThrow(() -> new FlightNotFoundException(id));
    }
}