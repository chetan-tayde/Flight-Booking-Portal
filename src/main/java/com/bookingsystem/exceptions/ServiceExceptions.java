package com.bookingsystem.exceptions;

public class ServiceExceptions {
	
	
	public static class FlightNotFoundException extends RuntimeException {
	    public FlightNotFoundException(String message) {
	        super(message);
	    }
	}
	
	public static class UserNotFoundException extends RuntimeException {
	    public UserNotFoundException(String message) {
	        super(message);
	    }
	}
	
	public static class SeatNotAvailableException extends RuntimeException {
	    public SeatNotAvailableException(String message) {
	        super(message);
	    }
	}
	
	public static class BookingFailedException extends RuntimeException {
	    public BookingFailedException(String message) {
	        super(message);
	    }
	}
	
	 public static class FlightSaveFailedException extends RuntimeException {
	        public FlightSaveFailedException(String message) {
	            super(message);
	        }
	    }
	 
	 public static class FlightSearchInvalidParametersException extends RuntimeException {
	        public FlightSearchInvalidParametersException(String message) {
	            super(message);
	        }
	    }
	 
	 public static class UserSaveFailedException extends RuntimeException {
	        public UserSaveFailedException(String message) {
	            super(message);
	        }
	    }
	 

}
