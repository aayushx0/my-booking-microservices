package io.bookingmicroservices.flight;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.TimeZone;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class FlightApiIntegrationTest {

    static {
        // Tests do not run main(), so set the timezone here (same Postgres issue as before).
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
    }

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createGetUpdateDeleteFlow() throws Exception {
        String createResponse = mockMvc.perform(post("/api/v1/flight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"flightNumber":"AI101","departureAirportCode":"DEL","arrivalAirportCode":"BOM","price":5500}
                                """))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(createResponse).get("id").asText();

        mockMvc.perform(get("/api/v1/flight/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value("AI101"));

        mockMvc.perform(put("/api/v1/flight/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"flightNumber":"AI202","departureAirportCode":"DEL","arrivalAirportCode":"BLR","price":6100}
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/flight/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value("AI202"))
                .andExpect(jsonPath("$.arrivalAirportCode").value("BLR"));

        mockMvc.perform(delete("/api/v1/flight/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/flight/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Flight not found"));
    }
}