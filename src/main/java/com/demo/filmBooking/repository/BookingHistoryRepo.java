package com.demo.filmBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.filmBooking.beans.BookingHistory;
import com.demo.filmBooking.beans.Customer;

@Repository
public interface BookingHistoryRepo extends JpaRepository<BookingHistory, Long>{

	List<BookingHistory> findByCustomer(Customer customer);

}
