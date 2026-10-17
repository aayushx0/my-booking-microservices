package io.bookingmicroservices.flight.flights.features.getflightbyid;

import io.bookingmicroservices.flight.flights.InMemoryFlightRepository;
import io.bookingmicroservices.flight.flights.models.Flight;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/flight")
public class GetFlightByIdController {

    private final InMemoryFlightRepository repository;

    public GetFlightByIdController(InMemoryFlightRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}