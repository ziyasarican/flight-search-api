package com.amadeus.flightsearchapi.controller;

import com.amadeus.flightsearchapi.model.dto.FlightResultDto;
import com.amadeus.flightsearchapi.model.dto.FlightSearchRequestDto;
import com.amadeus.flightsearchapi.model.dto.RoundTripFlightResultDto;
import com.amadeus.flightsearchapi.model.entity.Flights;
import com.amadeus.flightsearchapi.service.SearchAPIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/search-api")
@RequiredArgsConstructor
public class SearchAPIController {
    private final SearchAPIService searchService;

    @GetMapping
    public ResponseEntity<Object> searchFlights(@Valid FlightSearchRequestDto request) {
        try {
            if (request.getReturnDate() == null) {
                List<Flights> flights = searchService.searchOneWayFlights(request);
                return ResponseEntity.ok(new FlightResultDto(flights));
            } else {
                RoundTripFlightResultDto roundTripFlightResult = searchService.searchRoundTripFlights(request);
                return ResponseEntity.ok(roundTripFlightResult);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
