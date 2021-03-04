package com.techelevator.tenmo.controller;

import org.springframework.stereotype.Service;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransfersDAO;

@Service
public class TransfersServices {

	
	private TransfersDAO transfersDAO;
	private AccountDAO accountDAO;
	
	public TransfersServices (TransfersDAO transfersDAO, AccountDAO accountDAO) {
		this.transfersDAO = transfersDAO;
		this.accountDAO = accountDAO;
	}
	
	public Double viewBalances (Long userId) {
		return transfersDAO.viewBalance(userId);
		
	}
	
	
	
	
	
	
}
