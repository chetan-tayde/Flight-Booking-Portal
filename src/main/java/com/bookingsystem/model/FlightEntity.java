package com.bookingsystem.model;

import java.sql.Date;
import java.time.LocalTime;

import com.bookingsystem.helper.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import javax.validation.constraints.NotNull;

@Entity
public class FlightEntity {

	@Id
	private String flightId;

	@NotNull(message = "Source is mandatory")
	private String source;

	@NotNull(message = "Destination is mandatory")
	private String destination;

	@NotNull(message = "Seating capacity is mandatory")
	private int seatingCapacity;

	@NotNull(message = "Departure date is mandatory")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date depatureDate;

	@NotNull(message = "Departure time is mandatory")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private LocalTime departureTime;

	@NotNull(message = "Arrival date is mandatory")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date arrivalDate;

	@NotNull(message = "Arrival time is mandatory")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private LocalTime arrivalTime;

	@NotNull
	@JsonIgnore
	private int businessAvailableSeats;

	@NotNull
	@JsonIgnore
	private int economyAvailableSeats;

	@NotNull(message = "Price is mandatory")
	private double bussinessClassPrice;

	@NotNull(message = "Price is mandatory")
	private double economyClassPrice;
	
	@NotNull(message = "Airport source name is mandatory")
	private String airportSourceName;
	
	@NotNull(message = "Airport destination name is mandatory")
	private String aiportDestinationName;
	
	@NotNull(message = "Airport source city name is mandatory")
	private String aiportSourceCityName;
	
	@NotNull(message = "Airport destination city name is mandatory")
	private String airportDestinationCityName;

	@PrePersist
	public void generateFlightId() {
		if (this.flightId == null) {
			String sourceCode = (source != null && source.length() >= 3) ? source.substring(0, 3).toUpperCase() : "xxx";
			String destinationCode = (destination != null && destination.length() >= 3)
					? destination.substring(0, 3).toUpperCase()
					: "xxx";
			String flightIdBase = Constants.FLIGHTPREFIX + sourceCode + destinationCode;
			String flightIdNumber = String.format("%03d", (int) (Math.random() * 1000));
			this.flightId = flightIdBase + flightIdNumber;
		}

		// Initialize class-specific seats
		this.businessAvailableSeats = seatingCapacity / 2;
		this.economyAvailableSeats = seatingCapacity - businessAvailableSeats;
	}


	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(int seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

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

	public int getBusinessAvailableSeats() {
		return businessAvailableSeats;
	}

	public void setBusinessAvailableSeats(int businessAvailableSeats) {
		this.businessAvailableSeats = businessAvailableSeats;
	}

	public int getEconomyAvailableSeats() {
		return economyAvailableSeats;
	}

	public void setEconomyAvailableSeats(int economyAvailableSeats) {
		this.economyAvailableSeats = economyAvailableSeats;
	}

	public double getBussinessClassPrice() {
		return bussinessClassPrice;
	}

	public void setBussinessClassPrice(double bussinessClassPrice) {
		this.bussinessClassPrice = bussinessClassPrice;
	}

	public double getEconomyClassPrice() {
		return economyClassPrice;
	}

	public void setEconomyClassPrice(double economyClassPrice) {
		this.economyClassPrice = economyClassPrice;
	}


	public String getAirportSourceName() {
		return airportSourceName;
	}


	public void setAirportSourceName(String airportSourceName) {
		this.airportSourceName = airportSourceName;
	}


	public String getAiportDestinationName() {
		return aiportDestinationName;
	}


	public void setAiportDestinationName(String aiportDestinationName) {
		this.aiportDestinationName = aiportDestinationName;
	}


	public String getAiportSourceCityName() {
		return aiportSourceCityName;
	}


	public void setAiportSourceCityName(String aiportSourceCityName) {
		this.aiportSourceCityName = aiportSourceCityName;
	}


	public String getAirportDestinationCityName() {
		return airportDestinationCityName;
	}


	public void setAirportDestinationCityName(String airportDestinationCityName) {
		this.airportDestinationCityName = airportDestinationCityName;
	}
	
	

}
