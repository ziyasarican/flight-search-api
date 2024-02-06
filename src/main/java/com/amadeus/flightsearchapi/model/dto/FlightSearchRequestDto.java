package com.amadeus.flightsearchapi.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FlightSearchRequestDto {
    private LocalDateTime departureDate;
    private LocalDateTime returnDate;
    private String departureAirport;
    private String arrivalAirport;
}
