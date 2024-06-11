package com.demo.filmBooking.beans;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class BookingHistory {
    

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingHistoryId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private MovieShows shows;


    @Column
    @ElementCollection
    private List<String> seats;
    
    @Column
    private String showDate;

    @Column
    private Double totalPrice;


	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getShowDate() {
		return showDate;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public BookingHistory() {
    	System.out.println("***BookingHistory object created***");
	}

	public Long getBookingHistoryId() {
		return bookingHistoryId;
	}

	public void setBookingHistoryId(Long bookingHistoryId) {
		this.bookingHistoryId = bookingHistoryId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public MovieShows getShows() {
		return shows;
	}

	public void setShows(MovieShows shows) {
		this.shows = shows;
	}

	public List<String> getSeats() {
		return seats;
	}

	public void setSeats(List<String> seats) {
		this.seats = seats;
	}
	
	

    
}
