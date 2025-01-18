package com.bookingsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingsystem.exceptions.ServiceExceptions;
import com.bookingsystem.model.User;
import com.bookingsystem.repository.UserRepository;
import com.bookingsystem.service.UserService;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(@Valid User user) {

		if (userRepository.findByPhoneNumberOrEmailId(user.getPhoneNumber(), user.getEmailId()).isPresent()) {
			throw new ServiceExceptions.UserSaveFailedException(
					"User with the given phone number or email ID already exists.");
		}
		try {
			return userRepository.save(user);
		} catch (Exception e) {
			throw new ServiceExceptions.UserSaveFailedException("Failed to save the flight: " + e.getMessage());
		}
	}
}
