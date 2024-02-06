package com.amadeus.flightsearchapi.controller;

import com.amadeus.flightsearchapi.model.dto.FlightsDto;
import com.amadeus.flightsearchapi.service.FlightsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/flights")
public class FlightsController {
    private final FlightsService flightsService;

    @Autowired
    public FlightsController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }


    @GetMapping
    public ResponseEntity<List<FlightsDto>> getAllFlights() {
        List<FlightsDto> flights = flightsService.getAllFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Optional<FlightsDto> getFlightById(@PathVariable Long id) {
        return flightsService.getFlightById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveFlights(@RequestBody FlightsDto flightsDto) {
        try {
            flightsService.saveFlight(flightsDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Flight saved successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PostMapping("/save-batch")
    public ResponseEntity<String> saveFlights(@RequestBody List<FlightsDto> flightsDtoList) {
        try {
            flightsService.saveFlights(flightsDtoList);
            return ResponseEntity.status(HttpStatus.CREATED).body("Flights saved successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateFlights(@PathVariable Long id, @RequestBody FlightsDto flightsDto) {
        try {
            flightsService.updateFlight(id, flightsDto);
            return ResponseEntity.status(HttpStatus.OK).body("Flight updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        try {
            flightsService.deleteFlight(id);
            return ResponseEntity.status(HttpStatus.OK).body("Flight deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
