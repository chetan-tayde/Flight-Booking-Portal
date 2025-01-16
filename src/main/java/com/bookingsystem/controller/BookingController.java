package com.bookingsystem.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bookingsystem.helper.SeatClass;
import com.bookingsystem.service.BookingService;


@RestController
@RequestMapping("/api/booking")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	 @PostMapping("/book")
	    public ResponseEntity<Map<String, Object>> bookSeat(
	            @RequestParam String flightId,
	            @RequestParam int userId,
	            @RequestParam SeatClass seatClass) {
	        Map<String, Object> response = bookingService.bookSeat(flightId, userId, seatClass);
	        return ResponseEntity.ok(response);
	    }
	
	 @GetMapping("/{bookingId}")
	    public ResponseEntity<Map<String, Object>> getBookingDetails(@PathVariable String bookingId) {
	        Map<String, Object> bookingDetails = bookingService.getBookingDetailsByBookingId(bookingId);
	        return ResponseEntity.ok(bookingDetails);
	    }

}
