package com.bookingsystem.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bookingsystem.model.FlightEntity;
import com.bookingsystem.service.FlightService;

import jakarta.validation.Valid;

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
	    public ResponseEntity<List<Map<String, Object>>> searchFlights(@RequestParam Date departureDate, 
	                                                                   @RequestParam String source, 
	                                                                   @RequestParam String destination) {
	        List<Map<String, Object>> flights = flightService.searchFlights(departureDate, source, destination);
	        return ResponseEntity.ok(flights);
	    }
}
