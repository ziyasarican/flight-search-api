package com.amadeus.flightsearchapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Flights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "departure_airport_id")
    private Airports departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_airport_id")
    private Airports arrivalAirport;

    @Column(name = "departure_date_time")
    private LocalDateTime departureDateTime;

    @Column(name = "return_date_time")
    private LocalDateTime returnDateTime;

    @Column(name = "price")
    private double price;

}
