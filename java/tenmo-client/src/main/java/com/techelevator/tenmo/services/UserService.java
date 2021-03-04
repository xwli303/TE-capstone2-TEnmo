package com.techelevator.tenmo.services;



import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.User;
import com.techelevator.view.ConsoleService;

public class UserService {
	
	User user = new User();
	public static String AUTH_TOKEN = "";
	private String BASE_URL = "http://localhost:8080/";
	public RestTemplate restTemplate = new RestTemplate();
	//private final ConsoleService console = new ConsoleService(input, output);
	
	public UserService(String url) {
		BASE_URL = url;
	}
	
	public double getAccountBalance() throws UserNotFoundException {
		User user = null;
		user = restTemplate.exchange(BASE_URL, HttpMethod.GET, makeAuthEntity(), User.class).getBody();
		return user.getBalance();
		
		
	}

	private HttpEntity makeAuthEntity() {
		 HttpHeaders headers = new HttpHeaders();
	     headers.setBearerAuth(AUTH_TOKEN);
	     HttpEntity entity = new HttpEntity<>(headers);
	     return entity;
	}
}