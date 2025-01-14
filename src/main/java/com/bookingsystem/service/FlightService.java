package com.bookingsystem.service;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.bookingsystem.model.FlightEntity;
import com.bookingsystem.repository.FlightRepository;

@Service
@Validated
public class FlightService {
	
	@Autowired
	private FlightRepository flightRepository;
	
	 public FlightEntity saveFlight(@Valid FlightEntity flightEntity) {
	        return flightRepository.save(flightEntity);
	    }
	 
	 public List<FlightEntity> searchFlights(Date departureDate, String source, String destination) {
	        return flightRepository.findByDepatureDateAndSourceAndDestination(departureDate, source, destination);
	    }
}
