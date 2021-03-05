package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransfersDAO {
	
	Double viewBalance(Long userId);
	
	void create(Long userId, Long receiverId, Double amount);
	
	List<Transfer> viewTransfers(Long userId);  //view past transfers
	
	Transfer viewTransferDetails(Long transferId);
	
	
	
	//additionals
//	List <Transfers> viewPendingRequests(); //check return type
//	
//	void requestTransfers(Long accountId); //request transfers
//	
//	boolean approveOrDenyTransfers (); //?? bool?
	
}
