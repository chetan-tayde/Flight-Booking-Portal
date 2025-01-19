package com.bookingsystem.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.bookingsystem.helper.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({
		ServiceExceptions.BookingFailedException.class,
		ServiceExceptions.FlightNotFoundException.class,
		ServiceExceptions.SeatNotAvailableException.class,
		ServiceExceptions.UserNotFoundException.class,
		ServiceExceptions.FlightSaveFailedException.class,
		ServiceExceptions.FlightSearchInvalidParametersException.class,
		ServiceExceptions.UserSaveFailedException.class,
		ServiceExceptions.FlightValidationException.class
	})
	
	 public ResponseEntity<Object> handleCustomExceptions(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) 
                .body(Map.of("message", ex.getMessage()));
    }	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
	    ErrorResponse errorResponse = new ErrorResponse(errors);
	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(errorResponse);
	}
}
