package com.demo.filmBooking.beans;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    @Column
    private String seatNumber;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private MovieShows show;

    @ManyToMany(mappedBy = "seats")
    private List<BookingHistory> bookingHistories;

    public Seat() {
		System.out.println("***seat object created***");
	}

	public Long getId() {
		return seatId;
	}

	public void setId(Long id) {
		this.seatId = id;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public MovieShows getShow() {
		return show;
	}

	public void setShow(MovieShows show) {
		this.show = show;
	}

	public List<BookingHistory> getBookingHistories() {
		return bookingHistories;
	}

	public void setBookingHistories(List<BookingHistory> bookingHistories) {
		this.bookingHistories = bookingHistories;
	}

	@Override
	public String toString() {
		return "Seat [id=" + seatId + ", seatNumber=" + seatNumber + ", show=" + show + ", bookingHistories="
				+ bookingHistories + "]";
	}
    
    
}
