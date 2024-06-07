package com.demo.filmBooking.beans;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;


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
	
	@ManyToMany
    @JoinTable(
        name = "movie_theaters",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "theater_id")
    )
	@JsonManagedReference
    private List<Theater> theaters;
	

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

	public List<Theater> getTheaters() {
		return theaters;
	}

	public void setTheaters(List<Theater> theaters) {
		this.theaters = theaters;
	}

	@Override
	public String toString() {
		return "Movie [movieId=" + movieId + ", movieName=" + movieName + ", imageName=" + imageName + "]";
	}

	

	
	
	

	

	
	
	
	
}
