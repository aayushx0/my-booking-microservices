package io.bookingmicroservices.flight.data.jpa.repositories;

import io.bookingmicroservices.flight.data.jpa.entities.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlightJpaRepository extends JpaRepository<FlightEntity, UUID> {
}