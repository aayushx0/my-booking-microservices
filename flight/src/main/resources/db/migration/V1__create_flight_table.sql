CREATE TABLE flight (
    id UUID PRIMARY KEY,
    flight_number VARCHAR(10) NOT NULL,
    departure_airport_code VARCHAR(3) NOT NULL,
    arrival_airport_code VARCHAR(3) NOT NULL,
    price NUMERIC(10, 2) NOT NULL
);
