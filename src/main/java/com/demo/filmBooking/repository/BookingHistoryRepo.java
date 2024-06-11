package com.demo.filmBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.filmBooking.beans.BookingHistory;
import com.demo.filmBooking.beans.Customer;
import com.demo.filmBooking.beans.MovieShows;

@Repository
public interface BookingHistoryRepo extends JpaRepository<BookingHistory, Long>{

	List<BookingHistory> findByCustomer(Customer customer);

	@Query("select history.seats from BookingHistory history where history.shows= :shows and  history.showDate= :date ")
	List<String> findSeatsByShow(@Param("shows")MovieShows shows,@Param("date") String date);
	
	

}
