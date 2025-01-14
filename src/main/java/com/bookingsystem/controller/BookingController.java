package com.bookingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookingsystem.helper.SeatClass;
import com.bookingsystem.model.BookingEntity;
import com.bookingsystem.service.BookingService;


@RestController
@RequestMapping("/api/booking")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@PostMapping("/book")
    public BookingEntity bookSeat(
            @RequestParam String flightId,
            @RequestParam int userId,
            @RequestParam SeatClass seatClass) {
        return bookingService.bookSeat(flightId, userId, seatClass);
    }

}
