package com.demo.filmBooking.beans;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class MovieShows {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showId;
    
    @Column
    private String movieTiming;

   

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;


    public MovieShows() {
	   System.out.println("***show object created***");
   }

	public Long getShowId() {
		return showId;
	}

	public void setShowId(Long showId) {
		this.showId = showId;
	}

	public String getMovieTiming() {
		return movieTiming;
	}

	public void setMovieTiming(String movieTiming) {
		this.movieTiming = movieTiming;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Theater getTheater() {
		return theater;
	}

	public void setTheater(Theater theater) {
		this.theater = theater;
	}



	
    
    
}
