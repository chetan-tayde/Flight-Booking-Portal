package com.bookingsystem.service.impl;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingsystem.exceptions.ServiceExceptions;
import com.bookingsystem.model.FlightEntity;
import com.bookingsystem.repository.FlightRepository;
import com.bookingsystem.service.FlightService;

import jakarta.validation.Valid;

@Service
public class FlightServiceImpl implements FlightService{
	
	@Autowired
	private FlightRepository flightRepository;

	@Override
	public FlightEntity saveFlight(@Valid FlightEntity flightEntity) {
		try {
			return flightRepository.save(flightEntity);
		} catch (Exception e) {
			throw new ServiceExceptions.FlightSaveFailedException("Failed to save the flight: " + e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> searchFlights(Date departureDate, String source, String destination) {
		 if (source == null || source.isEmpty() || destination == null || destination.isEmpty()) {
	            throw new ServiceExceptions.FlightSearchInvalidParametersException("Source and Destination cannot be empty.");
	        }
		List<FlightEntity> flights = flightRepository.findByDepatureDateAndSourceAndDestination(departureDate, source,
				destination);
		if (flights.isEmpty()) {
         throw new ServiceExceptions.FlightNotFoundException("No flights found for the given criteria.");
     }

		return flights.stream().map(flight -> {
			Map<String, Object> flightData = new LinkedHashMap<>();
			flightData.put("Flight Id", flight.getFlightId());
			flightData.put("Source", flight.getSource());
			flightData.put("Destination", flight.getDestination());
			flightData.put("Depature Date", flight.getDepatureDate());
			flightData.put("Departure Time", flight.getDepartureTime());
			flightData.put("Arrival Date", flight.getArrivalDate());
			flightData.put("Arrival Time", flight.getArrivalTime());
			flightData.put("Business Class Available Seats", flight.getBusinessAvailableSeats());
			flightData.put("Economy Class Available Seats", flight.getEconomyAvailableSeats());
			flightData.put("Bussiness Class Price", flight.getBussinessClassPrice());
			flightData.put("Economy Class Price", flight.getEconomyClassPrice());
			flightData.put("Source Airport", flight.getAirportSourceName());
			flightData.put("Destination Airport", flight.getAiportDestinationName());
			flightData.put("Air Line", flight.getAirLineName());
			return flightData;
		}).collect(Collectors.toList());
	}

}
