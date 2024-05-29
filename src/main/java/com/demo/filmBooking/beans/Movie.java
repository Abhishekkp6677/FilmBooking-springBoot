package com.demo.filmBooking.beans;


import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie_details")
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long movieId;
	
	@Column(nullable = false)
	private String movieName;
	
	@Column
	private String imageName;
	
	@ElementCollection
    @Column(name = "theatre")
	private List<String> movieTheatre;
	
	
	public List<String> getMovieTheatre() {
		return movieTheatre;
	}

	public void setMovieTheatre(List<String> movieTheatre) {
		this.movieTheatre = movieTheatre;
	}

	public Movie() {
		System.out.println("movie object created");
	}

	public long getMovieId() {
		return movieId;
	}

	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public String toString() {
		return "Movie [movieId=" + movieId + ", movieName=" + movieName + ", imageName=" + imageName + ", movieTheatre="
				+ movieTheatre + "]";
	}

	
	
	
	
}
