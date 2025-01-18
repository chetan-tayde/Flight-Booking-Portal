package com.bookingsystem.service;

import com.bookingsystem.model.User;

import jakarta.validation.Valid;

public abstract class UserService {

	public User saveUser(@Valid User user) {
		return user;
	}
	
}
