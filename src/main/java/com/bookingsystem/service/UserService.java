package com.bookingsystem.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingsystem.model.User;
import com.bookingsystem.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	 public User saveUser(@Valid User user) {
	        return userRepository.save(user);
	    }

}
