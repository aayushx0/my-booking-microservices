package io.bookingmicroservices.flight.flights.features.getflights;

import io.bookingmicroservices.flight.data.jpa.entities.FlightEntity;
import io.bookingmicroservices.flight.data.jpa.repositories.FlightJpaRepository;
import io.bookingmicroservices.flight.flights.models.Flight;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flight")
public class GetFlightsController {

    private final FlightJpaRepository repository;

    public GetFlightsController(FlightJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Flight> getAll() {
        return repository.findAll().stream()
                .map(FlightEntity::toModel)
                .toList();
    }
}