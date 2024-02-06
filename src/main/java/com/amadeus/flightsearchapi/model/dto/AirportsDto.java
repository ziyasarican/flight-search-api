package com.amadeus.flightsearchapi.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AirportsDto {
    private Long id;
    private String city;
}
