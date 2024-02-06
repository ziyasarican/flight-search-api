package com.amadeus.flightsearchapi.controller;

import com.amadeus.flightsearchapi.model.dto.AirportsDto;
import com.amadeus.flightsearchapi.service.AirportsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/airports")
public class AirportsController {

    private final AirportsService airportsService;

    @Autowired
    public AirportsController(AirportsService airportsService) {
        this.airportsService = airportsService;
    }

    @GetMapping
    public List<AirportsDto> getAllAirports() {
        return airportsService.getAllAirports();
    }

    @GetMapping("/{id}")
    public Optional<AirportsDto> getAirportById(@PathVariable Long id) {
        return airportsService.getAirportById(id);
    }

    @PostMapping
    public ResponseEntity<String> saveAirport(@RequestBody AirportsDto airportsDto) {
        try {
            airportsService.saveAirport(airportsDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Airports saved successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
    @PostMapping("/save-batch")
    public ResponseEntity<String> saveAirports(@RequestBody List<AirportsDto> airportsDtoList) {
        try {
            airportsService.saveAirports(airportsDtoList);
            return ResponseEntity.status(HttpStatus.CREATED).body("Airports saved successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAirport(@PathVariable Long id, @RequestBody AirportsDto airportsDto) {
        try {
            airportsService.updateFlight(id, airportsDto);
            return ResponseEntity.status(HttpStatus.OK).body("Airport updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAirport(@PathVariable Long id) {
        try {
            airportsService.deleteAirport(id);
            return ResponseEntity.status(HttpStatus.OK).body("Airport deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }    }
}
