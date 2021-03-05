package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.model.Transfer;


@RestController
public class TransfersController {
	
	private TransfersServices transferServices;

	public TransfersController (TransfersServices transferServices) {
		this.transferServices = transferServices;
	}
	
	@RequestMapping(path = "/users/{id}/getbalances", method = RequestMethod.GET)
	public double viewBalance (@PathVariable Long id) {
		return transferServices.viewBalances(id);  //userid
	}
	
	@RequestMapping(path = "/users/{id}/all-transfers", method = RequestMethod.GET)
	public List<Transfer> allTransfers (@PathVariable Long id){
		return transferServices.viewTransfers(id);
	}
	
}
