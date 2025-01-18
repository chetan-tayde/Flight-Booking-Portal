package com.bookingsystem.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import com.bookingsystem.model.FlightEntity;
import jakarta.validation.Valid;


public interface FlightService {
	
	public FlightEntity saveFlight(@Valid FlightEntity flightEntity);
	
	public List<Map<String, Object>> searchFlights(Date departureDate, String source, String destination);

}
