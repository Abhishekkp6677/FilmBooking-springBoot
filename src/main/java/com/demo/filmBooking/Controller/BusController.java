package com.demo.filmBooking.Controller;

import java.io.IOException;
import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.demo.filmBooking.beans.Customer;
import com.demo.filmBooking.beans.Movie;
import com.demo.filmBooking.repository.MovieRepo;
import com.demo.filmBooking.service.CustomerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BusController {
	
	@Autowired
	private CustomerService service;
	
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
			System.out.println("****admin login successfull****");
			return "redirect:/admin";
			
		}
		
		else if(customer != null) {
			System.out.println("****login successfull****");
			session.setAttribute("username", customer.getCustomerName());
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
	public String admin(Model model) {
		System.out.println("****admin controller****");
		List<Movie> movie=service.getAllMovies();
		System.out.println(movie);
		model.addAttribute("movie", movie);
		return "admin.html";
		
		
	}
	
	@GetMapping("/admin/addMovie")
	public String addMovie() {
		System.out.println("****addmovie controller****");
		return "addMovie.html";
		
		
	}
	
	@PostMapping("/addMovie")
	public String addMovie(Movie movie, @RequestParam("movieImage") MultipartFile imageFile) {
        try {
            service.saveMovie(movie, imageFile);
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
		System.out.println(movie);

		return "editMovie.html";
	}
	
	@PostMapping("/editMovie")
	public String editMovie(@RequestParam Long movieId,
							@RequestParam String movieName,
							@RequestParam List<String> movieTheatre,
							@RequestParam("movieImage") MultipartFile imageFile) throws IOException {
		System.out.println(movieId+"*****");
		service.editMovie(movieId, movieName, movieTheatre ,imageFile);
		return "redirect:/admin";
		
	}
	@GetMapping("admin/deleteMovies/{id}")
	public String deleteMovie(@PathVariable Long id) throws IOException {
		service.deleteMovie(id);
		return "redirect:/admin";
		
	}

	
	
	
	
	
	
	
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		System.out.println("logged out");
		return "redirect:/";
	}


}












