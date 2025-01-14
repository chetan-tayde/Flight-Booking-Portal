package com.bookingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bookingsystem.helper.Constants;
import com.bookingsystem.helper.SeatClass;
import com.bookingsystem.model.BookingEntity;
import com.bookingsystem.model.FlightEntity;
import com.bookingsystem.model.User;
import com.bookingsystem.repository.BookingRepository;
import com.bookingsystem.repository.FlightRepository;
import com.bookingsystem.repository.UserRepository;

import java.util.Random;


@Service
public class BookingService {
	
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public BookingEntity bookSeat(String flightId,int userId, SeatClass seatClass) {
		
		FlightEntity flight = flightRepository.findById(flightId).orElseThrow(() -> new RuntimeException("Flight not found"));
		
		  // Retrieve the user from the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
       
		
        if (seatClass == SeatClass.BUSINESS && flight.getBusinessAvailableSeats() > 0) {
            return handleBooking(flight, user, seatClass, 1000);
        } else if (seatClass == SeatClass.ECONOMY && flight.getEconomyAvailableSeats() > 0) {
            return handleBooking(flight, user, seatClass, 700);
        }
        else {
            throw new RuntimeException("No available seats in " + seatClass + " class.");
        }
	}
        
        private BookingEntity handleBooking(FlightEntity flight, User user, SeatClass seatClass, double price) {
        	
            // Assign seat number and reduce available seats
            int seatNumber = flight.assignNextAvailableSeat(seatClass);
            flight.bookSeat(seatClass);
            flightRepository.save(flight);

            // Create and save booking entity
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
            
            return booking;
          
	}
}
