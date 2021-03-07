package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.model.Account;
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
	
	@Transactional
	public void createTransfer(Long userId, Long receiverId, Double amount) {
		transfersDAO.create(userId, receiverId, amount);		
	}
	
	List<Transfer> viewPendingRequests(Long userId){
		return transfersDAO.viewPendingRequests(userId);
	}

	public void createRequestForBucks(Long id, Long toUserId, Double amount) {
		transfersDAO.requestBucks(id, toUserId,amount);
		
	}
}
