package com.techelevator.tenmo.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransfersController {
	
	private TransfersServices transferServices;

	public TransfersController (TransfersServices transferServices) {
		this.transferServices = transferServices;
	}
	
	@RequestMapping(path = "/tranfers/{id}", method = RequestMethod.GET)
	public double viewBalance (@PathVariable Long id) {
		return transferServices.viewBalances(id);  //userid
	}
	
	
	
	
}
