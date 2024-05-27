package com.demo.filmBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.filmBooking.beans.Movie;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Long> {

	
}
