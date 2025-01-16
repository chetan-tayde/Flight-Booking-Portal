package com.bookingsystem.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookingsystem.exceptions.ServiceExceptions;
import com.bookingsystem.model.User;
import com.bookingsystem.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User saveUser(@Valid User user) {
		try {
			return userRepository.save(user);
		} catch (Exception e) {
			throw new ServiceExceptions.UserSaveFailedException("Failed to save the flight: " + e.getMessage());
		}
	}
}
