package com.demo.filmBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.filmBooking.beans.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
	
	 Customer findByCustomerEmailAndCustomerPassword(String email, String password);
		
	
}
