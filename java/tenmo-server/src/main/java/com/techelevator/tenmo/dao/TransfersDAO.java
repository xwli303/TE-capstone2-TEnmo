package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

public interface TransfersDAO {
	
	Double viewBalance(Long userId);
	
	void create(Long userId, Long receiverId, Double amount);
	
	List<Transfer> viewTransfers(Long userId);  //view past transfers
	
	Transfer viewTransferDetails(Long transferId);
	
	//additionals
	List <Transfer> viewPendingRequests(Long userId); //check return type

	void requestBucks(Long userId, Long receiverId, Double amount);

	
	

//	boolean approveOrDenyTransfers (); //bool?
	
}
