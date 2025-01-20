package com.bookingsystem.service;

import com.bookingsystem.model.User;

import jakarta.validation.Valid;

public interface UserService {

	public User saveUser(@Valid User user);
	
}
