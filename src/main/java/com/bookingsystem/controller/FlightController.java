package com.bookingsystem.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookingsystem.model.FlightEntity;
import com.bookingsystem.service.FlightService;

@RestController
@RequestMapping("/api/flights")
@Validated
public class FlightController {

	@Autowired
	private FlightService flightService;
	
	    @PostMapping
	    public ResponseEntity<FlightEntity> createFlight(@Valid @RequestBody FlightEntity flightEntity) {
	        FlightEntity savedFlight = flightService.saveFlight(flightEntity);
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedFlight);
	    }
	    
	    
	    @GetMapping("/search")
	    public List<Map<String, Object>> searchFlights(@RequestParam Date departureDate, 
	                                            @RequestParam String source, 
	                                            @RequestParam String destination) {
	    	
	    	List<FlightEntity> flights = flightService.searchFlights(departureDate, source, destination);
	        List<Map<String, Object>> response = flights.stream().map(flight -> {
	            Map<String, Object> flightData = new HashMap<>();
	            flightData.put("Flight Id" , flight.getFlightId());
	            flightData.put("source", flight.getSource());
	            flightData.put("destination", flight.getDestination());
	            flightData.put("depatureDate", flight.getDepatureDate());
	            flightData.put("departureTime", flight.getDepartureTime());
	            flightData.put("arrivalDate", flight.getArrivalDate());
	            flightData.put("arrivalTime", flight.getArrivalTime());
	            flightData.put("businessClassAvailableSeats", flight.getBusinessAvailableSeats());
	            flightData.put("economyClassAvailableSeats", flight.getEconomyAvailableSeats());
	            flightData.put("bussinessClassPrice",flight.getBussinessClassPrice());
	            flightData.put("economyClassPrice", flight.getEconomyClassPrice());
	            flightData.put("Airport Source", flight.getSourceAirport().getName());
	            flightData.put("Airport Destination" , flight.getDestinationAirport().getName());
	            return flightData;
	        }).collect(Collectors.toList());
	        return response;
	    }
	    
}
