package com.techelevator.tenmo.services;



import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.view.ConsoleService;

public class UserService {
	
	User user = new User();
	Account account = new Account();
	Transfer transfer = new Transfer();
	public static String AUTH_TOKEN = "";
	private String BASE_URL = "http://localhost:8080/";
	public RestTemplate restTemplate = new RestTemplate();
	//private final ConsoleService console = new ConsoleService(input, output);
	
	public UserService(String url) {
		BASE_URL = url;
	}
	
	public UserService (User user, Account account, Transfer transfer) {
		this.user = user;
		this.account = account;
		this.transfer = transfer;
	}
	
	
	public Integer getUserId()  {
		User user = null;
		user = restTemplate.exchange(BASE_URL + "/users", 
				HttpMethod.GET, makeAuthEntity(), User.class).getBody();
		return user.getId();	
	}
	
	//account get balance
	public Double getAccountBalance(Integer userId)  {
		Double balance = null;
		balance = restTemplate.exchange(BASE_URL + "/users/" + userId + "/balances", 
				HttpMethod.GET, makeAuthEntity(), Double.class).getBody();
		return balance;	
	}
	
	
	//get transfers list
	public Transfer[] allTransfers (Integer userId) {
		Transfer[] allTransfers = null;
		allTransfers = restTemplate.exchange(BASE_URL + "/users/" + userId + "/all-transfers", 
				HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
		return allTransfers;	
	}
	
	public Transfer transferDetails(Integer userId, Integer transferId) {
		Transfer transfer = null;
		transfer = restTemplate.exchange(BASE_URL + "/users/" + userId + "/" + transferId, 
				HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
		return transfer;
	}
	
	
	
	
	private HttpEntity makeAuthEntity() {
		 HttpHeaders headers = new HttpHeaders();
	     headers.setBearerAuth(AUTH_TOKEN);
	     HttpEntity entity = new HttpEntity<>(headers);
	     return entity;
	}
}