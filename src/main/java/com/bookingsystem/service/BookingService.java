package com.bookingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bookingsystem.helper.Constants;
import com.bookingsystem.helper.FlightManager;
import com.bookingsystem.helper.SeatClass;
import com.bookingsystem.model.BookingEntity;
import com.bookingsystem.model.FlightEntity;
import com.bookingsystem.model.User;
import com.bookingsystem.repository.BookingRepository;
import com.bookingsystem.repository.FlightRepository;
import com.bookingsystem.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private UserRepository userRepository;
	
	FlightManager flightManager = new FlightManager();

    @Transactional
    public Map<String, Object> bookSeat(String flightId, int userId, SeatClass seatClass) {
        FlightEntity flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        double price = (seatClass == SeatClass.BUSINESS)
                ? flight.getBussinessClassPrice()
                : flight.getEconomyClassPrice();

        if ((seatClass == SeatClass.BUSINESS && flight.getBusinessAvailableSeats() > 0) ||
            (seatClass == SeatClass.ECONOMY && flight.getEconomyAvailableSeats() > 0)) {
            return handleBooking(flight, user, seatClass, price);
        } else {
            throw new RuntimeException("No available seats in " + seatClass + " class.");
        }
    }


	private Map<String, Object> handleBooking(FlightEntity flight, User user, SeatClass seatClass, double price) {
		int seatNumber = flightManager.assignNextAvailableSeat(flight,seatClass);
		flightManager.bookSeat(flight,seatClass);
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
		
		 Map<String, Object> response = new HashMap<>();
		    response.put("message", "Booking is successful");
		    response.put("bookingId", bookingId);
		    response.put("flightId", flight.getFlightId());
		    response.put("source", flight.getSource());
		    response.put("destination", flight.getDestination());
		    response.put("depatureDate", flight.getDepatureDate());
		    response.put("departureTime", flight.getDepartureTime());
		    response.put("arrivalDate", flight.getArrivalDate());
		    response.put("arrivalTime", flight.getArrivalTime());
//		    response.put("Source Airport", flight.getSourceAirport().getName());
//		    response.put("Destination Airport", flight.getDestinationAirport().getName());
		    response.put("seatNumber", seatNumber);
		    response.put("seatClass", seatClass.name());
		    response.put("price", price); 
		return response;
	}

	public Map<String, Object> getBookingDetailsByBookingId(String bookingId) {
		BookingEntity booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("Booking not found"));
		FlightEntity flight = booking.getFlight();
		User user = booking.getUser();
		Map<String, Object> bookingDetails = new HashMap<>();

		// User details
		bookingDetails.put("username", user.getFirstName());
		bookingDetails.put("userEmail", user.getLastName());

		bookingDetails.put("departureTime", flight.getDepartureTime());
		bookingDetails.put("arrivalTime", flight.getArrivalTime());
		bookingDetails.put("departureDate", flight.getDepatureDate());
		bookingDetails.put("arrivalDate", flight.getArrivalDate());

		// Booking details
		bookingDetails.put("bookingId", booking.getBookingId());
		bookingDetails.put("price", booking.getPrice());
		bookingDetails.put("seatClass", booking.getSeatClass());

		bookingDetails.put("departureTime", flight.getDepartureTime());
		
		return bookingDetails;
	}
}
