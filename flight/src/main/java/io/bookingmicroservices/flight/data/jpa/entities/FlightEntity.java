package io.bookingmicroservices.flight.data.jpa.entities;

import io.bookingmicroservices.flight.flights.models.Flight;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "flight")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FlightEntity {

    @Id
    private UUID id;
    private String flightNumber;
    private String departureAirportCode;
    private String arrivalAirportCode;
    private BigDecimal price;

    public static FlightEntity from(Flight flight) {
        return new FlightEntity(
                flight.id(),
                flight.flightNumber(),
                flight.departureAirportCode(),
                flight.arrivalAirportCode(),
                flight.price());
    }

    public Flight toModel() {
        return new Flight(id, flightNumber, departureAirportCode, arrivalAirportCode, price);
    }
}