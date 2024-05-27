package com.demo.filmBooking.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.demo.filmBooking.beans.Customer;
import com.demo.filmBooking.beans.Movie;
import com.demo.filmBooking.service.CustomerService;

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
	public String login(@RequestParam String email,@RequestParam String password) {
		System.out.println("****login controller****");
		System.out.println(email);
		System.out.println(password);
		Customer customer=service.login(email, password);
		if("admin@gmail.com".equals(email) && "12345".equals(password)) {
			System.out.println("****admin login successfull****");
			return "redirect:/admin";
			
		}
		
		else if(customer != null) {
			System.out.println("****login successfull****");
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
	public String home(Model model) {
		System.out.println("****home controller****");
//		model.addAttribute("customer", new Customer());
		return "home.html";
	
	
	}
	
	@GetMapping("/admin")
	public String admin(Model model) {
		System.out.println("****admin controller****");
//		model.addAttribute("customer", new Customer());
		return "admin.html";
		
		
	}
	
	@PostMapping("/admin/addMovie")
	public String addMovie(Movie movie, @RequestParam("movieImage") MultipartFile imageFile) {
        try {
            service.saveMovie(movie, imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }
		
		
	}












