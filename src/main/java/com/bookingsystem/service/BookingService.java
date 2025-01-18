package com.bookingsystem.service;

import com.bookingsystem.helper.SeatClass;
import java.util.Map;

public interface BookingService {

	Map<String, Object> bookSeat(String flightId, int userId, SeatClass seatClass);
	
	Map<String, Object> getBookingDetailsByBookingId(String bookingId);
	
	
}
