package com.bookingsystem.service;

import com.bookingsystem.helper.SeatClass;
import com.bookingsystem.model.BookingEntity;

import java.util.List;
import java.util.Map;

public interface BookingService {

	Map<String, Object> bookSeat(String flightId, int userId, SeatClass seatClass);
	
	//Polymorphism method overloading
	Map<String, Object> getBooking(String bookingId);

	//Polymorphism method overloading
	List<BookingEntity> getBooking();
	
	
	
	
}
