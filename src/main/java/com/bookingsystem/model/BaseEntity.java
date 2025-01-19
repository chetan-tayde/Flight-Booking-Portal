package com.bookingsystem.model;

import java.sql.Date;
import java.time.LocalTime;

import com.bookingsystem.exceptions.ServiceExceptions;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

@MappedSuperclass
public abstract class BaseEntity {

	@NotNull(message = "Departure date cannot be empty")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	protected Date depatureDate;

	@NotNull(message = "Departure time cannot be empty")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	protected LocalTime departureTime;

	@NotNull(message = "Arrival date cannot be empty")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	protected Date arrivalDate;

	@NotNull(message = "Arrival time cannot be empty")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	protected LocalTime arrivalTime;

	public Date getDepatureDate() {
		return depatureDate;
	}

	public void setDepatureDate(Date depatureDate) {
		this.depatureDate = depatureDate;
	}

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void validateDatesAndTimes() {
		if (arrivalDate.before(depatureDate)) {
			throw new ServiceExceptions.FlightValidationException("Arrival date cannot be earlier than departure date. "
					+ "Departure Date: " + depatureDate + ", Arrival Date: " + arrivalDate);
		}
		if (arrivalDate.equals(depatureDate) && arrivalTime.isBefore(departureTime)) {
			throw new ServiceExceptions.FlightValidationException(
					"On the same date, arrival time cannot be earlier than departure time. " + "Departure Time: "
							+ departureTime + ", Arrival Time: " + arrivalTime);
		}
	}
}
