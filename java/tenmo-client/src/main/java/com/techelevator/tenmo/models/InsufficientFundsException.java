package com.techelevator.tenmo.models;

public class InsufficientFundsException extends Exception {
	private static final long serialVersionUID = 1L;
	
		public InsufficientFundsException(String message) {
			super(message);
}
}