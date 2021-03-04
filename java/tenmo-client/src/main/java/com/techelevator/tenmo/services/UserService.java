package com.techelevator.tenmo.services;



import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.User;
import com.techelevator.view.ConsoleService;

public class UserService {
	
	User user = new User();
	Account account = new Account();
	public static String AUTH_TOKEN = "";
	private String BASE_URL = "http://localhost:8080/";
	public RestTemplate restTemplate = new RestTemplate();
	//private final ConsoleService console = new ConsoleService(input, output);
	
	public UserService(String url) {
		BASE_URL = url;
	}
	
	//account
	public double getAccountBalance(Long userId)  {
		Double balance = null;
		balance = restTemplate.exchange(BASE_URL + "/users/" + userId + "/balances", 
				HttpMethod.GET, makeAuthEntity(), Double.class).getBody();
		return balance;
		
		
	}
	
//	public double getBalance(Integer userId) {
//		
//		Account account = null;
//		account = restTemplate.getForObject(BASE_URL + "/users/" + userId, Account.class);
//		return account.getBalance();
//		
//	}

	private HttpEntity makeAuthEntity() {
		 HttpHeaders headers = new HttpHeaders();
	     headers.setBearerAuth(AUTH_TOKEN);
	     HttpEntity entity = new HttpEntity<>(headers);
	     return entity;
	}
}