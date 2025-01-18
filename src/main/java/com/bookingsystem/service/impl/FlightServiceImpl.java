package com.bookingsystem.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookingsystem.exceptions.ServiceExceptions;
import com.bookingsystem.model.FlightEntity;
import com.bookingsystem.repository.FlightRepository;
import com.bookingsystem.service.FlightService;
import jakarta.validation.Valid;

//Implementation of interface oops concept
@Service
public class FlightServiceImpl implements FlightService{
	
	private static final Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);
	
	@Autowired
	private FlightRepository flightRepository;

	@Override
	public FlightEntity saveFlight(@Valid FlightEntity flightEntity) {
		try {
			logger.info("Attempting to save flight with details: {}", flightEntity);
			return flightRepository.save(flightEntity);
		} catch (Exception e) {
			logger.error("Failed to save the flight: {}", e.getMessage(), e);
			throw new ServiceExceptions.FlightSaveFailedException("Failed to save the flight: " + e.getMessage());
		}
	}

	//Polymorphism Method Overloading
	@Override
	public List<Map<String, Object>> searchFlights(Date departureDate, String source, String destination) {
		 if (source == null || source.isEmpty() || destination == null || destination.isEmpty()) {
			 logger.warn("Source or Destination is empty: Source = {}, Destination = {}", source, destination);
	            throw new ServiceExceptions.FlightSearchInvalidParametersException("Source and Destination cannot be empty.");
	        }
		 logger.info("Searching for flights with Departure Date: {}, Source: {}, Destination: {}", departureDate, source, destination);
		List<FlightEntity> flights = flightRepository.findByDepatureDateAndSourceAndDestination(departureDate, source,
				destination);
		if (flights.isEmpty()) {
			logger.warn("No flights found for the given criteria: Departure Date = {}, Source = {}, Destination = {}", departureDate, source, destination);
         throw new ServiceExceptions.FlightNotFoundException("No flights found for the given criteria.");
     }

		logger.info("Found {} flights matching the criteria.", flights.size());
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

	//Polymorphism Method Overloading
	@Override
	public List<Map<String, Object>> searchFlights(String airportSourceName, String aiportDestinationName) {
		if(airportSourceName == null || airportSourceName.isEmpty() || aiportDestinationName == null || aiportDestinationName.isEmpty()) {
			logger.warn("Airport Source Name or Airport Destination Name is empty: Source = {}, Destination = {}", airportSourceName, aiportDestinationName);
			throw new ServiceExceptions.FlightSearchInvalidParametersException("Source and Destination cannot be empty.");
		}
		
		logger.info("Searching for flights with Airport Source Name: {}, Airport Destination Name: {}", airportSourceName, aiportDestinationName);
		List<FlightEntity> flights = flightRepository.findByAirportSourceNameAndAiportDestinationName(airportSourceName, aiportDestinationName);
		if(flights.isEmpty()) {
			logger.warn("No flights found for the given criteria: Source = {}, Destination = {}", airportSourceName, aiportDestinationName);
			throw new ServiceExceptions.FlightNotFoundException("No flights found for the given criteria.");
		}
		
		logger.info("Found {} flights matching the criteria.", flights.size());
		List<Map<String, Object>> responseList = new ArrayList<>();
		for (FlightEntity flight: flights) {
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
			responseList.add(flightData);
		}
		return responseList;
	}

}
