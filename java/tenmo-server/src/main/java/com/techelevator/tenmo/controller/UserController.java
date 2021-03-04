package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.User;

@RestController
public class UserController {
	
	private UserDAO userDAO;
	
	public UserController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	//no
	@RequestMapping(path = "/users?username=", method = RequestMethod.GET)
	public int findIdByUsername(@RequestParam String userName) {
		int userId = userDAO.findIdByUsername(userName);
		return userId;
	}
	//yes
	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public List<User> findAll() {
		return userDAO.findAll();
	}
	//yes
	@RequestMapping(path = "/{username}", method = RequestMethod.GET)
	public User findByUsername(@PathVariable String username){
		return userDAO.findByUsername(username);
	}
	
	//does not work
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "/users", method = RequestMethod.POST)
	public boolean create(@RequestBody User user, String username, @RequestBody String password) {
		userDAO.create(username, password);
		return true;
		//??????????????? 
		
	}
	//yes
	@RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
	public double getBalance(@PathVariable int id) {
		return userDAO.getBalance(id);
	}
	

}
