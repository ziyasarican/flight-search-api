package com.amadeus.flightsearchapi.repository;

import com.amadeus.flightsearchapi.model.entity.Airports;
import com.amadeus.flightsearchapi.model.entity.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightsRepository extends JpaRepository<Flights, Long> {
    List<Flights> findByDepartureAirportAndArrivalAirportAndDepartureDateTime(
            Airports departureAirport, Airports arrivalAirport, LocalDateTime departureDateTime);
}
