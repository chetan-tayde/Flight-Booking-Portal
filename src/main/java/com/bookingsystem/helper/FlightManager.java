package com.bookingsystem.helper;

import com.bookingsystem.model.FlightEntity;

public class FlightManager {

	public int assignNextAvailableSeat(FlightEntity flight, SeatClass seatClass) {
		if (seatClass == SeatClass.BUSINESS && flight.getBusinessAvailableSeats() > 0) {
			return flight.getSeatingCapacity() / 2 - flight.getBusinessAvailableSeats() + 1;
		} else if (seatClass == SeatClass.ECONOMY && flight.getEconomyAvailableSeats() > 0) {
			return flight.getSeatingCapacity() - flight.getEconomyAvailableSeats() + 1;
		}
		throw new RuntimeException("No seats available in " + seatClass + " class.");
	}

	public void bookSeat(FlightEntity flight, SeatClass seatClass) {
		if (seatClass == SeatClass.BUSINESS) {
			if (flight.getBusinessAvailableSeats() > 0) {
				flight.setBusinessAvailableSeats(flight.getBusinessAvailableSeats() - 1);
			} else {
				throw new RuntimeException("No available seats in BUSINESS class.");
			}
		} else if (seatClass == SeatClass.ECONOMY) {
			if (flight.getEconomyAvailableSeats() > 0) {
				flight.setEconomyAvailableSeats(flight.getEconomyAvailableSeats() - 1);
			} else {
				throw new RuntimeException("No available seats in ECONOMY class.");
			}
		}
	}
}


