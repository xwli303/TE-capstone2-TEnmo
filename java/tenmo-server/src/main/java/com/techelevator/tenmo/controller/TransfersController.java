package com.techelevator.tenmo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;


@RestController
public class TransfersController {
	private TransfersServices transferServices;

	public TransfersController (TransfersServices transferServices) {
		this.transferServices = transferServices;
	}
	
	@RequestMapping(path = "/users/{id}/getbalances", method = RequestMethod.GET)
	public double viewBalance (@PathVariable @Valid Long id) {
		return transferServices.viewBalances(id);  //userid
	}
	
	@RequestMapping(path = "/users/{id}/all-transfers", method = RequestMethod.GET)
	public List<Transfer> allTransfers (@PathVariable @Valid Long id){
		return transferServices.viewTransfers(id);
	}
	
	@RequestMapping(path = "/users/{id}/{transferId}", method = RequestMethod.GET)
	public Transfer transferDetails (@Valid @PathVariable Long id, @PathVariable Long transferId) {
		return transferServices.viewTransferDetails(id, transferId);	
	}
	
	@RequestMapping(path = "/transfer/{id}", method = RequestMethod.POST)
	public void sendTransfer (@RequestBody Transfer transfer, @PathVariable Long id, Long receiverId, Double amount) {
		 transferServices.createTransfer(transfer.getFromUserId(), transfer.getToUserId(), transfer.getAmount()); //from userId
		
	}
	
	@RequestMapping(path = "/transfer/{id}/pending-transfers", method = RequestMethod.GET)
	public List<Transfer> pendingTransfers(@PathVariable Long id){
		return transferServices.viewPendingRequests(id);
	}
	
	@RequestMapping(path = "{id}/transfer/request-bucks", method = RequestMethod.POST)
	public void requestBucks (@RequestBody Transfer transfer, @PathVariable Long id, Long toUserId, Double amount) {
		transferServices.createRequestForBucks(transfer.getFromUserId(), transfer.getToUserId(), transfer.getAmount());
	}

}
