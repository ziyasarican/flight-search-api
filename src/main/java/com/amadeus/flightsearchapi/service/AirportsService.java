package com.amadeus.flightsearchapi.service;

import com.amadeus.flightsearchapi.model.dto.AirportsDto;
import com.amadeus.flightsearchapi.model.entity.Airports;
import com.amadeus.flightsearchapi.model.mapper.AirportsMapper;
import com.amadeus.flightsearchapi.repository.AirportsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirportsService {
    private final AirportsRepository airportsRepository;

    @Autowired
    public AirportsService(AirportsRepository airportsRepository) {
        this.airportsRepository = airportsRepository;
    }

    public List<AirportsDto> getAllAirports() {
        List<Airports> airportsList = airportsRepository.findAll();
        if(!airportsList.isEmpty()){
            return airportsList.stream()
                    .map(AirportsMapper::toDto)
                    .collect(Collectors.toList());
        } else{
            throw new RuntimeException("Flight List is Empty!");
        }
    }

    public Optional<AirportsDto> getAirportById(Long id) {
        return airportsRepository.findById(id)
                .map(AirportsMapper::toDto)
                .map(Optional::ofNullable)
                .orElseThrow(() -> new EntityNotFoundException("Could not data for id: " + id));
    }

    public void saveAirport(AirportsDto airportsDto) {
        Airports airports = Airports.builder()
                .city(airportsDto.getCity())
                .build();

        airportsRepository.save(airports);
    }

    public void deleteAirport(Long id) {
        Airports airports = airportsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Airport not found with id: " + id));

        airportsRepository.delete(airports);
    }

    public void updateFlight(Long id, AirportsDto airportsDto) {
        Airports existingFAirports = airportsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + id));

        existingFAirports.setCity(airportsDto.getCity());

        airportsRepository.save(existingFAirports);
    }

    public void saveAirports(List<AirportsDto> airportsDtoList) {
        List<Airports> airportsList = airportsDtoList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        airportsRepository.saveAll(airportsList);
    }

    private Airports convertToEntity(AirportsDto airportsDto) {
        return Airports.builder()
                .city(airportsDto.getCity())
                .build();
    }
}
