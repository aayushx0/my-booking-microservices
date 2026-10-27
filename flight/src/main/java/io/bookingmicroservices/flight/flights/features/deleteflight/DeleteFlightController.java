package io.bookingmicroservices.flight.flights.features.deleteflight;

import io.bookingmicroservices.flight.data.jpa.repositories.FlightJpaRepository;
import io.bookingmicroservices.flight.flights.exceptions.FlightNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/flight")
public class DeleteFlightController {

    private final FlightJpaRepository repository;

    public DeleteFlightController(FlightJpaRepository repository) {
        this.repository = repository;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!repository.existsById(id)) {
            throw new FlightNotFoundException(id);
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}