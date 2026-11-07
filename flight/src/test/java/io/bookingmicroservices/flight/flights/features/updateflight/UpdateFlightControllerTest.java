package io.bookingmicroservices.flight.flights.features.updateflight;

import io.bookingmicroservices.flight.data.jpa.entities.FlightEntity;
import io.bookingmicroservices.flight.data.jpa.repositories.FlightJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UpdateFlightController.class)
class UpdateFlightControllerTest {

    private static final String VALID_BODY = """
            {"flightNumber":"AI202","departureAirportCode":"DEL","arrivalAirportCode":"BLR","price":6100}
            """;

    private static final String INVALID_BODY = """
            {"flightNumber":"","departureAirportCode":"DEL","arrivalAirportCode":"BLR","price":-5}
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FlightJpaRepository repository;

    @Test
    void updatesAnExistingFlight() throws Exception {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);

        mockMvc.perform(put("/api/v1/flight/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value("AI202"));

        verify(repository).save(any(FlightEntity.class));
    }

    @Test
    void returns404WhenTheFlightDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);

        mockMvc.perform(put("/api/v1/flight/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Flight not found"));

        verify(repository, never()).save(any(FlightEntity.class));
    }

    @Test
    void returns400WhenTheBodyIsInvalid() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(put("/api/v1/flight/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(INVALID_BODY))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(repository);
    }
}