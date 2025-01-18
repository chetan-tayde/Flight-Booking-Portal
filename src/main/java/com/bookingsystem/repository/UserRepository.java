package com.bookingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookingsystem.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
		
	Optional<User> findByPhoneNumberOrEmailId(String phoneNumber, String emailId);
}
