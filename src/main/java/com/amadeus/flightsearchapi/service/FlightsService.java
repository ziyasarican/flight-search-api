package com.amadeus.flightsearchapi.service;

import com.amadeus.flightsearchapi.model.dto.FlightsDto;
import com.amadeus.flightsearchapi.model.entity.Airports;
import com.amadeus.flightsearchapi.model.entity.Flights;
import com.amadeus.flightsearchapi.model.mapper.FlightsMapper;
import com.amadeus.flightsearchapi.repository.AirportsRepository;
import com.amadeus.flightsearchapi.repository.FlightsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightsService {

    private final FlightsRepository flightsRepository;
    private final AirportsRepository airportsRepository;

    @Autowired
    public FlightsService(FlightsRepository flightsRepository, AirportsRepository airportsRepository) {
        this.flightsRepository = flightsRepository;
        this.airportsRepository = airportsRepository;
    }

    public List<FlightsDto> getAllFlights() {
        List<Flights> flightList = flightsRepository.findAll();

        if(!flightList.isEmpty()){
            return flightList.stream()
                    .map(FlightsMapper::toDto)
                    .collect(Collectors.toList());
        } else{
            throw new RuntimeException("Flight List is Empty!");
        }
    }

    public Optional<FlightsDto> getFlightById(Long id) {
        return flightsRepository.findById(id)
                .map(FlightsMapper::toDto)
                .map(Optional::ofNullable)
                .orElseThrow(() -> new EntityNotFoundException("Could not data for id: " + id));
    }



    public void saveFlight(FlightsDto flightsDto) {

        Airports departureAirport = airportsRepository.findByCity(flightsDto.getDepartureAirport())
                .orElseThrow(() -> new IllegalArgumentException("Invalid departure airport: " + flightsDto.getDepartureAirport()));

        Airports arrivalAirport = airportsRepository.findByCity(flightsDto.getArrivalAirport())
                .orElseThrow(() -> new IllegalArgumentException("Invalid arrival airport: " + flightsDto.getArrivalAirport()));

        Flights flight = Flights.builder()
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureDateTime(flightsDto.getDepartureDate())
                .returnDateTime(flightsDto.getReturnDate())
                .price(flightsDto.getPrice())
                .build();

        flightsRepository.save(flight);
    }

    public void saveFlights(List<FlightsDto> flightsDtoList) {
        List<Flights> flightsList = flightsDtoList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        flightsRepository.saveAll(flightsList);
    }

    private Flights convertToEntity(FlightsDto flightsDto) {
        return Flights.builder()
                .departureAirport(airportsRepository.findByCity(flightsDto.getDepartureAirport()).orElseThrow(() -> new IllegalArgumentException("Invalid departure airport: " + flightsDto.getDepartureAirport())))
                .arrivalAirport(airportsRepository.findByCity(flightsDto.getArrivalAirport()).orElseThrow(() -> new IllegalArgumentException("Invalid arrival airport: " + flightsDto.getArrivalAirport())))
                .departureDateTime(flightsDto.getDepartureDate())
                .returnDateTime(flightsDto.getReturnDate())
                .price(flightsDto.getPrice())
                .build();
    }

    public void updateFlight(Long id, FlightsDto flightsDto) {
        Flights existingFlight = flightsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + id));

        existingFlight.setDepartureAirport(airportsRepository.findByCity(flightsDto.getDepartureAirport())
                .orElseThrow(() -> new IllegalArgumentException("Invalid departure airport: " + flightsDto.getDepartureAirport())));
        existingFlight.setArrivalAirport(airportsRepository.findByCity(flightsDto.getArrivalAirport())
                .orElseThrow(() -> new IllegalArgumentException("Invalid arrival airport: " + flightsDto.getArrivalAirport())));
        existingFlight.setDepartureDateTime(flightsDto.getDepartureDate());
        existingFlight.setReturnDateTime(flightsDto.getReturnDate());
        existingFlight.setPrice(flightsDto.getPrice());

        flightsRepository.save(existingFlight);
    }

    public void deleteFlight(Long id) {
        Flights existingFlight = flightsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + id));

        flightsRepository.delete(existingFlight);
    }
}
