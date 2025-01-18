package com.bookingsystem.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookingsystem.model.FlightEntity;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, String>{
	
	@Query("SELECT flights FROM FlightEntity flights WHERE flights.depatureDate = :depatureDate and flights.source = :source and flights.destination = :destination")
	List<FlightEntity> findByDepatureDateAndSourceAndDestination(@Param("depatureDate") Date depatureDate,@Param("source") String source,@Param("destination") String destination);
	
	@Query("SELECT flights FROM FlightEntity flights WHERE flights.airportSourceName = :airportSourceName and flights.aiportDestinationName=:aiportDestinationName")
	List<FlightEntity> findByAirportSourceNameAndAiportDestinationName(@Param ("airportSourceName")String airportSourceName, @Param("aiportDestinationName") String aiportDestinationName);
}
