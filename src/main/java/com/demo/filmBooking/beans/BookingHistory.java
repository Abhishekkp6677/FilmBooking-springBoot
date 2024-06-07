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

    @ManyToMany
    @JoinTable(
        name = "booking_history_show",
        joinColumns = @JoinColumn(name = "booking_history_id"),
        inverseJoinColumns = @JoinColumn(name = "show_id")
    )
    private List<MovieShows> shows;

    @ManyToMany
    @JoinTable(
        name = "booking_history_seat",
        joinColumns = @JoinColumn(name = "booking_history_id"),
        inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<Seat> seats;
    
    @Column
    private String showDate;
    
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

	public List<MovieShows> getShows() {
		return shows;
	}

	public void setShows(List<MovieShows> shows) {
		this.shows = shows;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

    
}
