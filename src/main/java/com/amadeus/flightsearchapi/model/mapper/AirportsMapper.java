package com.amadeus.flightsearchapi.model.mapper;

import com.amadeus.flightsearchapi.model.dto.AirportsDto;
import com.amadeus.flightsearchapi.model.entity.Airports;

public class AirportsMapper {

    public static AirportsDto toDto(Airports airport){
        return new AirportsDto(airport.getId(),airport.getCity());
    }
}
