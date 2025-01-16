package com.bookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookingsystem.model.BookingEntity;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, String>{

}
