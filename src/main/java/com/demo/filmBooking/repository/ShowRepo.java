package com.demo.filmBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.filmBooking.beans.Movie;
import com.demo.filmBooking.beans.MovieShows;
import com.demo.filmBooking.beans.Theater;

public interface ShowRepo extends JpaRepository<MovieShows, Long>{
	

	List<MovieShows> findByMovie(Movie movie);

	@Query("select ms.movieTiming from MovieShows ms where ms.movie.movieId= :movieId AND ms.theater.theaterId = :theaterId")
	List<String> findMovieTimingByTheater(@Param("movieId") Long movieId, @Param("theaterId") Long theaterId);

	MovieShows findByTheaterAndMovieAndMovieTiming(Theater theater, Movie movie, String movieTiming);

	MovieShows findByTheaterAndMovieTiming(Theater theater, String movieTiming);

	
}
