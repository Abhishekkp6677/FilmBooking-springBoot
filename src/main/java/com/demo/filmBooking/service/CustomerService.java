package com.demo.filmBooking.service;

import java.io.IOException; 
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.demo.filmBooking.beans.BookingHistory;
import com.demo.filmBooking.beans.Customer;
import com.demo.filmBooking.beans.Movie;
import com.demo.filmBooking.beans.MovieShows;
import com.demo.filmBooking.beans.Theater;
import com.demo.filmBooking.repository.BookingHistoryRepo;
import com.demo.filmBooking.repository.CustomerRepo;
import com.demo.filmBooking.repository.MovieRepo;
import com.demo.filmBooking.repository.ShowRepo;
import com.demo.filmBooking.repository.TheaterRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Component
public class CustomerService {
	
	@Autowired
	private CustomerRepo repo;
	
	@Autowired
	private MovieRepo movieRepo;
	
	@Autowired
	private TheaterRepo theaterRepo;

	@Autowired
	private ShowRepo showRepo;

	@Autowired
	private BookingHistoryRepo bookingHistoryRepo;
	
	
	
	public void register(Customer customer)
	{
		repo.save(customer);
	}
	
	public Customer login(String email, String passsword)
	{
		return repo.findByCustomerEmailAndCustomerPassword(email, passsword);
	}
	
	 

	private final String UPLOAD_DIR = "src/main/resources/static/images/";

	 public Movie saveMovie( String movieName ,MultipartFile imageFile,String theatersJson) throws IOException {
		 
		Movie movie= new Movie();
		movie.setMovieName(movieName);
		
	    if (!imageFile.isEmpty()) {
			String imageName = imageFile.getOriginalFilename();
			Path path = Paths.get(UPLOAD_DIR + imageName);
			Files.write(path, imageFile.getBytes());
			movie.setImageName(imageName);
	    }
	      
	    ObjectMapper objectMapper = new ObjectMapper();
	    List<Map<String, String>> theatersList = objectMapper.readValue(theatersJson, new TypeReference<List<Map<String, String>>>() {});
	    List<Theater> theaters = new ArrayList<Theater>();
	    for (Map<String, String> theaterMap : theatersList) {
	    	String theaterName = theaterMap.get("value");
	        Theater theater = theaterRepo.findByTheaterName(theaterName);
	        if (theater == null) {
	        	theater = new Theater();
	            theater.setTheaterName(theaterName);
	            theaterRepo.save(theater);
	        }
	        theaters.add(theater);
	        }
	    	
	    movie.setTheaters(theaters);
	    
	    movieRepo.save(movie);
	    return movie;	}
	 
	 public List<Movie> getAllMovies() {
		List<Movie> Movies =movieRepo.findAll();
		 return Movies;      
	}
	 
	 public Optional<Movie> getMovieById(Long id) {
		 Optional<Movie> movie =movieRepo.findById(id);
		return movie;
		     
	 }
	 
	 

	public void editMovie(Long id, String movieName, MultipartFile imageFile, String theatersJson) throws IOException {
		Movie movie=movieRepo.findById(id).orElseThrow();
		 if (!imageFile.isEmpty()) {
				String imageName = imageFile.getOriginalFilename();
				Path path = Paths.get(UPLOAD_DIR + imageName);
				Files.write(path, imageFile.getBytes());
				movie.setImageName(imageName);
	        }
		 
		 ObjectMapper objectMapper = new ObjectMapper();
		    List<Map<String, String>> theatersList = objectMapper.readValue(theatersJson, new TypeReference<List<Map<String, String>>>() {});
		    List<Theater> theaters = new ArrayList<Theater>();
		    for (Map<String, String> theaterMap : theatersList) {
		    	String theaterName = theaterMap.get("value");
		        Theater theater = theaterRepo.findByTheaterName(theaterName);
		        if (theater == null) {
		        	theater = new Theater();
		            theater.setTheaterName(theaterName);
		            theaterRepo.save(theater);
		        }
		        theaters.add(theater);
		        }
		    	
		    movie.setTheaters(theaters);
		    movie.setMovieName(movieName);
		 
		    movieRepo.save(movie);
	}
	
	public void deleteMovie(Long id) {
		movieRepo.deleteById(id);
	}

	public List<Theater> getAllTheaters() {
		List<Theater> theaters=theaterRepo.findAll();
		return theaters;
		
	}

	public List<Theater> findTheatersByMovie(Long movieId) {
		Optional<Movie> movie = movieRepo.findById(movieId);
        if (movie.isPresent()) {
            return movie.get().getTheaters();
        }
        return new ArrayList<>();
	}
	
	public void saveShows(MovieShows movieShows) {
		showRepo.save(movieShows);
	}

	public void saveShows(Movie movie, Theater theater, String movieTiming) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		MovieShows movieShows= new MovieShows();
		
			
			movieShows.setMovie(movie);
			movieShows.setTheater(theater);
			movieShows.setMovieTiming(movieTiming);

		
		showRepo.save(movieShows);
		
	}

	public List<MovieShows> getAllShows() {
		List<MovieShows> shows=showRepo.findAll();
		return shows;
	}

	public List<MovieShows> getShowByMovieId(Movie movie) {
		List<MovieShows> shows=showRepo.findByMovie(movie);
		return shows;
	}



	public List<String> findMovieTimingByTheater(Long movieId, Long theaterId) {
		// TODO Auto-generated method stub
		return showRepo.findMovieTimingByTheater( movieId,  theaterId);
	}

	public BookingHistory addBookingHistory(Long customerId,
											String date,
											String theaterName ,
											String movieTiming,
											Long id, List<String> seats,
											double amount) {
		
		Customer customer=repo.findById(customerId).orElseThrow();
		Theater theater= theaterRepo.findByTheaterName(theaterName);
		Movie movie = movieRepo.findById(id).orElseThrow();
		MovieShows shows= showRepo.findByTheaterAndMovieAndMovieTiming(theater,movie,movieTiming);
		
		BookingHistory history= new BookingHistory();
		history.setShowDate(date);
		history.setCustomer(customer);
		history.setShows(shows);
		history.setSeats(seats);
		history.setTotalPrice(amount);
//		System.out.println(history);
		return bookingHistoryRepo.save(history);
		
	}

	public List<BookingHistory> getHistory(Long id) {
		
		Customer customer=repo.findById(id).orElseThrow();
		return bookingHistoryRepo.findByCustomer(customer);
		
	}

	

	public Theater getTheaterByTheaterName(String theaterName) {
		
		return theaterRepo.findByTheaterName(theaterName);
	}

	public MovieShows getShowsByTheaterAndMovieTiming(Theater theater, String movieTiming) {
		
		return showRepo.findByTheaterAndMovieTiming(theater, movieTiming);
		
	}

	public List<String> getSeatsByShows(MovieShows shows, String date) {
		// TODO Auto-generated method stub
		return bookingHistoryRepo.findSeatsByShow(shows,date); 
	}


	
}
