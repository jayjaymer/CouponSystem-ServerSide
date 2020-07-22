package com.jay.exceptions;

public class CustomerNotFoundException extends Exception {


	public CustomerNotFoundException(String message) {
		super("Customer was not found, please try again!");
	}
	


}
