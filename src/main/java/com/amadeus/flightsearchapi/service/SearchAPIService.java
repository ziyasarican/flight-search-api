package com.amadeus.flightsearchapi.service;

import com.amadeus.flightsearchapi.model.dto.FlightSearchRequestDto;
import com.amadeus.flightsearchapi.model.dto.RoundTripFlightResultDto;
import com.amadeus.flightsearchapi.model.entity.Flights;
import com.amadeus.flightsearchapi.repository.AirportsRepository;
import com.amadeus.flightsearchapi.repository.FlightsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SearchAPIService {
    private final FlightsRepository flightRepository;
    private final AirportsRepository airportsRepository;


    public SearchAPIService(FlightsRepository flightRepository, AirportsRepository airportsRepository) {
        this.flightRepository = flightRepository;
        this.airportsRepository = airportsRepository;
    }

    public List<Flights> searchOneWayFlights(FlightSearchRequestDto request) {
        validateRequest(request);
        return flightRepository.findByDepartureAirportAndArrivalAirportAndDepartureDateTime(
                airportsRepository.findByCity(request.getDepartureAirport())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid departure airport: " + request.getDepartureAirport())),
                airportsRepository.findByCity(request.getArrivalAirport())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid arrival airport: " + request.getArrivalAirport())),
                request.getDepartureDate()
        );
    }

    public RoundTripFlightResultDto searchRoundTripFlights(FlightSearchRequestDto request) {
        validateRequest(request);
        List<Flights> outboundFlights = flightRepository.findByDepartureAirportAndArrivalAirportAndDepartureDateTime(
                airportsRepository.findByCity(request.getDepartureAirport())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid departure airport: " + request.getDepartureAirport())),
                airportsRepository.findByCity(request.getArrivalAirport())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid arrival airport: " + request.getArrivalAirport())),
                request.getDepartureDate()
        );
        List<Flights> returnFlights = flightRepository.findByDepartureAirportAndArrivalAirportAndDepartureDateTime(
                airportsRepository.findByCity(request.getArrivalAirport())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid departure airport: " + request.getArrivalAirport())),
                airportsRepository.findByCity(request.getDepartureAirport())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid arrival airport: " + request.getDepartureAirport())),
                request.getReturnDate()
        );
        return new RoundTripFlightResultDto(outboundFlights, returnFlights);
    }

    private void validateRequest(FlightSearchRequestDto request) {
        if (request.getDepartureDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Departure date cannot be in the past");
        }
        if (request.getReturnDate() != null && request.getReturnDate().isBefore(request.getDepartureDate())) {
            throw new IllegalArgumentException("Return date cannot be before departure date");
        }
    }
}
