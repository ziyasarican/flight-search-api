package com.amadeus.flightsearchapi.model.dto;


import com.amadeus.flightsearchapi.model.entity.Flights;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FlightResultDto {
    private List<Flights> flights;

}
