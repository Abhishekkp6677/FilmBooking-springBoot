package com.demo.filmBooking.beans;


import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name="customer_table")
public class Customer {

	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private long customerId;
	
	

	@Column(nullable = false)
	private String customerName;
	
	@Column(nullable = false, unique = true)
	private String customerEmail;
	
	@Column(nullable = false)
	private String customerPassword;
	
	
	@Column(nullable = false)
	private String customerNumber;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingHistory> bookingHistory;
	
	
	
	public Customer() {
		System.out.println("customer object created");
	}

	

	

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public List<BookingHistory> getBookingHistory() {
		return bookingHistory;
	}

	public void setBookingHistory(List<BookingHistory> bookingHistory) {
		this.bookingHistory = bookingHistory;
	}
	
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", customerEmail="
				+ customerEmail + ", customerPassword=" + customerPassword + ", customerNumber=" + customerNumber
				+ ", bookingHistory=" + bookingHistory + "]";
	}
	
	


	
}
