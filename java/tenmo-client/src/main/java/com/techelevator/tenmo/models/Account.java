package com.techelevator.tenmo.models;

public class Account {
	
	private Long accountId;
	private Long userId;
	private double balance;
	
	public Account() {
		
	}
	
	public Account(Long accountId, Long userId, double balance) {
		this.accountId = accountId;
		this.userId = userId;
		this.balance = balance;
	}
	
	
	public Long getAccountId() {
		return accountId;
	}
	
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}

}
