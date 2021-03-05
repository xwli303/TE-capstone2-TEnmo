package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.model.Transfer;

@Service
public class TransfersServices {

	
	private TransfersDAO transfersDAO;
	private AccountDAO accountDAO;
	
	public TransfersServices (TransfersDAO transfersDAO, AccountDAO accountDAO) {
		this.transfersDAO = transfersDAO;
		this.accountDAO = accountDAO;
	}
	
	public Double viewBalances (Long id) {
		return transfersDAO.viewBalance(id);
		
	}
	
	
	public List<Transfer> viewTransfers(Long userId){
		return transfersDAO.viewTransfers(userId);
	}
	
	public Transfer viewTransferDetails (Long userId, Long transferId) {
		return transfersDAO.viewTransferDetails(transferId);
	}
	
}
