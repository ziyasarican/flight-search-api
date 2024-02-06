package com.amadeus.flightsearchapi.model.mapper;

import com.amadeus.flightsearchapi.model.dto.FlightsDto;
import com.amadeus.flightsearchapi.model.entity.Flights;




public class FlightsMapper {

    public static FlightsDto toDto(Flights flights){
        return FlightsDto.builder()
                .id(flights.getId())
                .departureAirport(flights.getDepartureAirport().toString())
                .arrivalAirport(flights.getArrivalAirport().toString())
                .departureDate(flights.getDepartureDateTime())
                .returnDate(flights.getReturnDateTime())
                .price(Double.valueOf(flights.getPrice()))
                .build();
    }
}
