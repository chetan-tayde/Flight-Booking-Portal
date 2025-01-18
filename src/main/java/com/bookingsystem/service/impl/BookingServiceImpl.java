package com.bookingsystem.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingsystem.exceptions.ServiceExceptions;
import com.bookingsystem.helper.Constants;
import com.bookingsystem.helper.FlightManager;
import com.bookingsystem.helper.SeatClass;
import com.bookingsystem.model.BookingEntity;
import com.bookingsystem.model.FlightEntity;
import com.bookingsystem.model.User;
import com.bookingsystem.repository.BookingRepository;
import com.bookingsystem.repository.FlightRepository;
import com.bookingsystem.repository.UserRepository;
import com.bookingsystem.service.BookingService;

import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService{
	
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    private FlightManager flightManager = new FlightManager();

	@Override
	@Transactional
	public Map<String, Object> bookSeat(String flightId, int userId, SeatClass seatClass) {
		FlightEntity flight = flightRepository.findById(flightId).orElseThrow(
				() -> new ServiceExceptions.FlightNotFoundException("Flight not found for id: " + flightId));

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ServiceExceptions.UserNotFoundException("User not found for id: " + userId));

		double price = (seatClass == SeatClass.BUSINESS) ? flight.getBussinessClassPrice()
				: flight.getEconomyClassPrice();

		if ((seatClass == SeatClass.BUSINESS && flight.getBusinessAvailableSeats() > 0)
				|| (seatClass == SeatClass.ECONOMY && flight.getEconomyAvailableSeats() > 0)) {
			return handleBooking(flight, user, seatClass, price);
		} else {
			throw new ServiceExceptions.SeatNotAvailableException("Seat not available");
		}	
	}
	
	private Map<String, Object> handleBooking(FlightEntity flight, User user, SeatClass seatClass, double price) {
		int seatNumber = flightManager.assignNextAvailableSeat(flight, seatClass);
		flightManager.bookSeat(flight, seatClass);
		flightRepository.save(flight);
		BookingEntity booking = new BookingEntity();
		Random random = new Random();
		int sevenDigitNumber = 1000000 + random.nextInt(9000000);
		String bookingId = Constants.BOOKINGIDPREFIX + sevenDigitNumber;
		booking.setBookingId(bookingId);
		booking.setUser(user);
		booking.setFlight(flight);
		booking.setSeatNumber(seatNumber);
		booking.setSeatClass(seatClass.name());
		booking.setPrice(price);
		bookingRepository.save(booking);

		Map<String, Object> response = new LinkedHashMap<>();
		response.put("Status", "Booking is successful");
		response.put("Booking Id", bookingId);
		response.put("Flight Id", flight.getFlightId());
		response.put("Source", flight.getSource());
		response.put("Destination", flight.getDestination());
		response.put("Depature Date", flight.getDepatureDate());
		response.put("Departure Time", flight.getDepartureTime());
		response.put("Arrival Date", flight.getArrivalDate());
		response.put("Arrival Time", flight.getArrivalTime());
		response.put("Seat Number", seatNumber);
		response.put("Seat Class", seatClass.name());
		response.put("Price", price);
		response.put("Airport Source Name" , flight.getAirportSourceName());
		response.put("Airport Destination Name" , flight.getAiportDestinationName());
		
		return response;
	}

	@Override
	public Map<String, Object> getBookingDetailsByBookingId(String bookingId) {
		BookingEntity booking = bookingRepository.findById(bookingId).orElseThrow(
				() -> new ServiceExceptions.BookingFailedException("Booking not found for id: " + bookingId));
		FlightEntity flight = booking.getFlight();
		User user = booking.getUser();
		Map<String, Object> bookingDetails = new LinkedHashMap<>();
		bookingDetails.put("Booking Id", booking.getBookingId());
		bookingDetails.put("User Id", user.getUserId());
		bookingDetails.put("First Name", user.getFirstName());
		bookingDetails.put("Last Name", user.getLastName());
		bookingDetails.put("Seat Number", booking.getSeatNumber());
		bookingDetails.put("Departure Date", flight.getDepatureDate());
		bookingDetails.put("Departure Time", flight.getDepartureTime());
		bookingDetails.put("Arrival Date", flight.getArrivalDate());
		bookingDetails.put("Arrival Time", flight.getArrivalTime());
		bookingDetails.put("Source Airport", flight.getAirportSourceName());
		bookingDetails.put("Destination Airport", flight.getAiportDestinationName());
		bookingDetails.put("Price", booking.getPrice());
		bookingDetails.put("Seat Class", booking.getSeatClass());
		return bookingDetails;
	}
}
