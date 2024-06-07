package com.demo.filmBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.filmBooking.beans.Movie;
import com.demo.filmBooking.beans.MovieShows;

public interface ShowRepo extends JpaRepository<MovieShows, Long>{
	
//	@Query("SELECT ms FROM MovieShows ms WHERE ms.movieId = :movieId")
	List<MovieShows> findByMovie(Movie movie);
}
