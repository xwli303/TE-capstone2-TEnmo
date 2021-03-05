package com.techelevator.tenmo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.model.Transfer;


@RestController
//@PreAuthorize("isAuthenticated()")
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
	
	//client-userservice-sendbucks
	@RequestMapping(path = "/transfer/{id}", method = RequestMethod.POST)
	public void sendTransfer (@RequestBody Transfer transfer, @PathVariable Long id) {
		
		 transferServices.createTransfer(transfer); //from userId
	}
}
