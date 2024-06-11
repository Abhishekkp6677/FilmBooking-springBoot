package com.demo.filmBooking.DTO;

import java.util.List;

public class SeatSelectionRequest {

	private List<String> seats;
	
	private double price;

	

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<String> getSeats() {
		return seats;
	}

	public void setSeats(List<String> seats) {
		this.seats = seats;
	}
	
}
