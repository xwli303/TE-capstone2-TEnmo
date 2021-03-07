package com.techelevator.tenmo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.User;

@RestController
public class UserController {
	
	private UserDAO userDAO;
	private AccountDAO accountDAO;
	
	public UserController(UserDAO userDAO, AccountDAO accountDAO) {
		this.userDAO = userDAO;
		this.accountDAO = accountDAO;
	}
//create new object and post through
	//work on client side to get user and id to use
	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public List<User> findAll() {
		return userDAO.findAll();
	}
	
	@RequestMapping(path = "/{username}", method = RequestMethod.GET)
	public User findByUsername(@Valid @PathVariable String username){
		return userDAO.findByUsername(username);
	}
	
	@RequestMapping(path = "/users/{id}/balances", method = RequestMethod.GET)
	public double getBalance(@Valid @PathVariable Long id) {
		return accountDAO.getBalance(id);
	}
	
}
	


