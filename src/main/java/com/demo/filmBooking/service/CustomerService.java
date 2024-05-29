package com.demo.filmBooking.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.demo.filmBooking.beans.Customer;
import com.demo.filmBooking.beans.Movie;
import com.demo.filmBooking.repository.CustomerRepo;
import com.demo.filmBooking.repository.MovieRepo;

@Component
public class CustomerService {
	
	@Autowired
	private CustomerRepo repo;
	
	@Autowired
	private MovieRepo movieRepo;
	
	
	
	public void register(Customer customer)
	{
		repo.save(customer);
	}
	
	public Customer login(String email, String passsword)
	{
		return repo.findByCustomerEmailAndCustomerPassword(email, passsword);
	}
	
	 

	private final String UPLOAD_DIR = "src/main/resources/static/images/";

	 public Movie saveMovie(Movie movie, MultipartFile imageFile) throws IOException {
	      if (!imageFile.isEmpty()) {
				String imageName = imageFile.getOriginalFilename();
				Path path = Paths.get(UPLOAD_DIR + imageName);
				Files.write(path, imageFile.getBytes());
				movie.setImageName(imageName);
	        }
	        movieRepo.save(movie);
	    return movie;	
	}
	 
	 public List<Movie> getAllMovies() {
		List<Movie> Movies =movieRepo.findAll();
		 return Movies;      
	}
	 
	 public Optional<Movie> getMovieById(Long id) {
		 Optional<Movie> movie =movieRepo.findById(id);
		return movie;
		     
	 }
	 
	 

	public void editMovie(Long id, String movieName, List<String> movieTheatre, MultipartFile imageFile) throws IOException {
		Movie movie=movieRepo.findById(id).orElseThrow();
		 movie.setMovieName(movieName);
		 movie.setMovieTheatre(movieTheatre);
		 if (!imageFile.isEmpty()) {
				String imageName = imageFile.getOriginalFilename();
				Path path = Paths.get(UPLOAD_DIR + imageName);
				Files.write(path, imageFile.getBytes());
				movie.setImageName(imageName);
	        }
		movieRepo.save(movie);
	}
	
	public void deleteMovie(Long id) {
		movieRepo.deleteById(id);
	}
	
}
