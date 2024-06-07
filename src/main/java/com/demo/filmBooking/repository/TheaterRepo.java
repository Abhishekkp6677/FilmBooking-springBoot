package com.demo.filmBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.filmBooking.beans.Theater;

@Repository
public interface TheaterRepo extends JpaRepository<Theater, Long>{

	 Theater findByTheaterName(String theaterName) ;
	 
}
