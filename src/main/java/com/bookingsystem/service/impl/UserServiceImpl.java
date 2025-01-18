package com.bookingsystem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingsystem.exceptions.ServiceExceptions;
import com.bookingsystem.model.User;
import com.bookingsystem.repository.UserRepository;
import com.bookingsystem.service.UserService;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl extends UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(@Valid User user) {
		
		log.info("Attempting to save user with phone number: {} and email ID: {}", user.getPhoneNumber(), user.getEmailId());

		if (userRepository.findByPhoneNumberOrEmailId(user.getPhoneNumber(), user.getEmailId()).isPresent()) {
			log.warn("User with phone number: {} or email ID: {} already exists.", user.getPhoneNumber(), user.getEmailId());
			throw new ServiceExceptions.UserSaveFailedException(
					"User with the given phone number or email ID already exists.");
		}
		try {
			return userRepository.save(user);
		} catch (Exception e) {
			log.error("Failed to save user: {}", e.getMessage(), e);
			throw new ServiceExceptions.UserSaveFailedException("Failed to save the flight: " + e.getMessage());
		}
	}
}
