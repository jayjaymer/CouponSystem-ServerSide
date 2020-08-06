package com.couponsystem.jay.exceptions;

public class LoginFailledException extends Exception {

	public LoginFailledException() {
		String message = "Sorry, The information is incorrent, Please try again!";
		System.out.println(message);
		
	}

	public LoginFailledException(String message) {
		super(message);
	}
	
	

}
