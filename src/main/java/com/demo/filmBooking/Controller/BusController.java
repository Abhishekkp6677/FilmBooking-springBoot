package com.demo.filmBooking.Controller;

import java.io.IOException; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.demo.filmBooking.DTO.SeatSelectionRequest;
import com.demo.filmBooking.beans.BookingHistory;
import com.demo.filmBooking.beans.Customer;
import com.demo.filmBooking.beans.Movie;
import com.demo.filmBooking.beans.MovieShows;
import com.demo.filmBooking.beans.Theater;
import com.demo.filmBooking.service.CustomerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BusController {
	
	@Autowired
	private CustomerService service;
	
	public boolean isLoggedIn(HttpSession session) {
		if (session.getAttribute("userId") != null) {
			return true;
		}else {
			return false;
		}
	}
	
	@GetMapping("/login")
	public String loginPage() {
		System.out.println("****home controller****");
		return "login.html";

	}
	
	@PostMapping("/login")
	public String login(@RequestParam String email,@RequestParam String password,HttpSession session) {
		System.out.println("****login controller****");
//		System.out.println(email);
//		System.out.println(password);
		Customer customer=service.login(email, password);
		if("admin@gmail.com".equals(email) && "12345".equals(password)) {
			session.setAttribute("admin", "true");
			System.out.println("****admin login successfull****");
			return "redirect:/admin";
			
		}
		
		else if(customer != null) {
			System.out.println("****login successfull****");
			session.setAttribute("username", customer.getCustomerName());
			session.setAttribute("userId", customer.getCustomerId());
			return "redirect:/";
			
		}else {
			
			System.out.println("****login unsuccessfull****");
			return "redirect:/register";
		}
		
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		System.out.println("****register controller****");
//		model.addAttribute("customer", new Customer());
		return "register.html";
		
	}
	
	@PostMapping("/register")
	public String userRegister(Customer customer) {
		System.out.println("****save controller****");
		System.out.println(customer.toString());
		service.register(customer);
		return "redirect:/login";
		
	}
	
	@GetMapping("/")
	public String home(Model model,HttpSession session) {
		System.out.println("****home controller****");
//		model.addAttribute("customer", new Customer());
		List<Movie> movies=service.getAllMovies();
		model.addAttribute("movies", movies);
		String name=(String) session.getAttribute("username");
		model.addAttribute("username",name);
		
		
		
		return "home.html";
	
	
	}
	
	@GetMapping("/admin")
	public String admin(Model model,HttpSession session) {
		if(session.getAttribute("admin")!=null) {
			
			System.out.println("****admin controller****");
			List<Movie> movie=service.getAllMovies();
			System.out.println(movie);
			model.addAttribute("movie", movie);
			return "admin.html";
		}else {
			return "redirect:/login";
		}
		
		
		
	}
	
	@GetMapping("/admin/addMovie")
	public String addMovie() {
		System.out.println("****addmovie controller****");
		return "addMovie.html";
		
		
	}
	
	@PostMapping("/addMovie")
	public String addMovie(@RequestParam("movieName")String movieName,
						   @RequestParam("movieImage") MultipartFile imageFile,
						   @RequestParam("theaters") String theatersJson) {
        try {
            service.saveMovie(movieName, imageFile, theatersJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }
		
		
	@GetMapping("/admin/editMovies/{id}")
	public String editMovie(Model model, @PathVariable Long id) {
		System.out.println("****editMovie controller****");
		Movie movie=service.getMovieById(id).orElseThrow();
		model.addAttribute("movie", movie);
		List<Theater> theaters=movie.getTheaters();
		String theaterNames= theaters.stream().map(Theater::getTheaterName).collect(Collectors.joining(","));
		System.out.println(theaterNames);
		model.addAttribute("theaterNames", theaterNames);

		return "editMovie.html";
	}
	
	@PostMapping("/editMovie")
	public String editMovie(@RequestParam Long movieId,
							@RequestParam String movieName,
							@RequestParam("movieImage") MultipartFile imageFile,
							@RequestParam("theaters") String theatersJson) throws IOException {
		System.out.println(movieId+"*****");
		service.editMovie(movieId, movieName,imageFile,theatersJson);
		return "redirect:/admin";
		
	}
	
	@GetMapping("admin/deleteMovies/{id}")
	public String deleteMovie(@PathVariable Long id) throws IOException {
		service.deleteMovie(id);
		return "redirect:/admin";
		
	}

	@GetMapping("admin/shows")
	public String shows(Model model) {
		
		List<MovieShows> shows =service.getAllShows();
		model.addAttribute("shows",shows);
		System.out.println(shows);
		return "shows.html";
		
	}
	
	@GetMapping("admin/addShows")
	public String addShowspage(Model model) {
		List<Movie> movies=service.getAllMovies();
		List<Theater> theaters=service.getAllTheaters();
		model.addAttribute("movies",movies);
		model.addAttribute("theaters",theaters);
		System.out.println(movies);
		return "addShows.html";
		
	}

	@GetMapping("admin/addShows/{movieId}")
	@ResponseBody
	public List<Theater> getTheatersByMovie(@PathVariable Long movieId) {
		
		List<Theater> theater=service.findTheatersByMovie(movieId);
		return theater;
		
		
	}
	
	@PostMapping("/addShows")
	public String addShowspage(@RequestParam Movie movie,
							   @RequestParam Theater theater,
							   @RequestParam String movieTiming) {
//		System.out.println(movie);
//		System.out.println(theater);
//		System.out.println(movieTiming);
		service.saveShows( movie, theater, movieTiming);
		
		return "redirect:/admin/shows";
		
	}
	
	@GetMapping("/bookTicket/{movieId}")
	public String getShows(Model model,@PathVariable Long movieId,HttpSession session) {
		if (isLoggedIn(session)) {
			
			session.setAttribute("movieId", movieId);
			Movie movie = service.getMovieById(movieId).orElseThrow();
			List<MovieShows> shows=service.getShowByMovieId(movie);
			model.addAttribute("shows", shows);
			
			Map<Theater, List<String>> theaterShowTimings = new HashMap();
			for (MovieShows movieShows : shows) {
				Theater theater = movieShows.getTheater();
				List<String> timings= service.findMovieTimingByTheater(movieId, theater.getTheaterId());
				theaterShowTimings.put(theater, timings);
				
			}
			
			model.addAttribute("theaterShowTimings", theaterShowTimings);
//			System.out.println(theaterShowTimings);
			
			Long id= (Long) session.getAttribute("userId");
			model.addAttribute("userId",id);
			
			
			
			
			return "bookTicket.html";
		}else {
			return "redirect:/login";
		}
		 
	}
	
	@PostMapping("/selectSeats")
	public String getSeats( 
							@RequestParam("movieDate")String date,
						 	@RequestParam("theaterName")String theaterName,
						 	@RequestParam("movieTiming")String movieTiming,
						 	HttpSession session,
						 	Model model)
	{
//		System.out.println(date);
//		System.out.println(theaterName);
//		System.out.println(movieTiming);
		session.setAttribute("showDate", date);
		session.setAttribute("theaterName", theaterName);
		session.setAttribute("movieTiming", movieTiming);
		
		Theater theater=service.getTheaterByTheaterName(theaterName);
		
		MovieShows shows =service.getShowsByTheaterAndMovieTiming(theater,movieTiming);
		
		List< String> seats= service.getSeatsByShows(shows,date);
		
		model.addAttribute("selectedSeats", seats);
		
		System.out.println("seats: "+seats);
		
		
		
		
		return "selectSeats.html";
		
	}
	
	@PostMapping("/selectSeats/bookTicket")
	public ResponseEntity<String> bookTicket(@RequestBody SeatSelectionRequest request,HttpSession session) {
		
		System.out.println("postmapping");
//		System.out.println(request.getSeats());
		System.out.println(request.getPrice());
//		System.out.println(request);
		Long customerId = (Long) session.getAttribute("userId") ;
		String date = (String) session.getAttribute("showDate") ;
		String theaterName  = (String) session.getAttribute("theaterName") ;
		String movieTiming= (String) session.getAttribute("movieTiming") ;
		Long id= (Long) session.getAttribute("movieId");

		service.addBookingHistory(customerId,date,theaterName,movieTiming,id,request.getSeats(),request.getPrice());
        return ResponseEntity.ok("Booking successful");

		
	}
	
	@GetMapping("/bookingHistory")
	public String bookingHistory(HttpSession session, Model model) {
		
		if (isLoggedIn(session)) {
			
			Long id=(Long) session.getAttribute("userId");
			List<BookingHistory> history=service.getHistory(id);
			model.addAttribute("history",history);
			return "bookingHistory.html";
		}else {
			return "redirect:/login";
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		System.out.println("logged out");
		return "redirect:/";
	}


}












