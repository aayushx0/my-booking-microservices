package io.bookingmicroservices.flight.flights;

import io.bookingmicroservices.flight.flights.models.Flight;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryFlightRepository {

    private final Map<UUID, Flight> store = new ConcurrentHashMap<>();

    public Flight save(Flight flight) {
        store.put(flight.id(), flight);
        return flight;
    }

    public Optional<Flight> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }
}