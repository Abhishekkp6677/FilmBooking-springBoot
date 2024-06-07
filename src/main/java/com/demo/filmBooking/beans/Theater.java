package com.demo.filmBooking.beans;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theaterId;
    @Column
    private String theaterName;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<MovieShows> shows;

    @ManyToMany(mappedBy = "theaters")
    @JsonBackReference
    private List<Movie> movies;

    public Theater() {
		System.out.println("***theater object created***");
    }

	public Long getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(Long theaterId) {
		this.theaterId = theaterId;
	}

	public String getTheaterName() {
		return theaterName;
	}

	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}

	public List<MovieShows> getShows() {
		return shows;
	}

	public void setShows(List<MovieShows> shows) {
		this.shows = shows;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public String toString() {
		return "Theater [theaterId=" + theaterId + ", theaterName=" + theaterName + "]";
	}

	
   
}
