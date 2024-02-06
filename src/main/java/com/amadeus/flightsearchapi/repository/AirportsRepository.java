package com.amadeus.flightsearchapi.repository;

import com.amadeus.flightsearchapi.model.entity.Airports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportsRepository extends JpaRepository<Airports, Long> {
    Optional<Airports> findByCity(String city);

}
