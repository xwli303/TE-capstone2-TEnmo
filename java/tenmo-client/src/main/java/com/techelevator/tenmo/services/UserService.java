package com.techelevator.tenmo.services;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.InsufficientFundsException;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferExceptions;
import com.techelevator.tenmo.models.User;
import com.techelevator.view.ConsoleService;

public class UserService {	
	User user = new User();
	Account account = new Account();
	Transfer transfer = new Transfer();
	public static String AUTH_TOKEN = "";
	private String BASE_URL = "http://localhost:8080/";
	public RestTemplate restTemplate = new RestTemplate();
	
	public UserService(String url) {
		BASE_URL = url;
	}
	
	public UserService (User user, Account account, Transfer transfer) {
		this.user = user;
		this.account = account;
		this.transfer = transfer;
	}
	
	public Integer getUserId() throws UserNotFoundException {
		User user = null;
		try {
			user = restTemplate.exchange(BASE_URL + "/users", 
					HttpMethod.GET, makeAuthEntity(), User.class).getBody();
		} catch (RestClientResponseException e) {
			throw new UserNotFoundException("User Not Found");
		}
		return user.getId();	
	}
	
	//account get balance
	public Double getAccountBalance(Integer userId) throws UserNotFoundException {
		Double balance = null;
		try {
			balance = restTemplate.exchange(BASE_URL + "/users/" + userId + "/balances", 
					HttpMethod.GET, makeAuthEntity(), Double.class).getBody();
		} catch (RestClientResponseException e) {
			throw new UserNotFoundException("Invalid ID");
		}
		return balance;	
	}
	
	//get all users
	public User[] getAllUsers() throws UserNotFoundException {
		User[] getAllUsers = null;
		try {
			getAllUsers = restTemplate.exchange(BASE_URL + "/users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
			
		}catch (RestClientResponseException e) {
			throw new UserNotFoundException(e.getMessage());
		}
		return getAllUsers;
	}
	
	//get transfers list
	public Transfer[] allTransfers (Integer userId) throws TransferExceptions {
		Transfer[] allTransfers = null;
		try {
			allTransfers = restTemplate.exchange(BASE_URL + "users/" + userId + "/all-transfers", 
					HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
		} catch (RestClientResponseException e) {
			throw new TransferExceptions(e.getMessage());
		}
		return allTransfers;
		
	}
	
	public Transfer transferDetails(Integer userId, Integer transferId) throws TransferExceptions{
		Transfer transfer = null;
		try {
			transfer = restTemplate.exchange(BASE_URL + "/users/" + userId + "/" + transferId, 
					HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
		} catch (RestClientResponseException e) {
			throw new TransferExceptions(e.getMessage());
		} 
			return transfer;
	}
	
	//passing an entire transfer object over 
	public void sendBucks(Transfer transfer) throws UserNotFoundException   {   
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity <Transfer> entity = new HttpEntity<>(transfer, headers);
		String url = BASE_URL + "/transfer/" + transfer.getFromUserId();
		try {
			restTemplate.postForObject(url, entity, Transfer.class);
		} catch (RestClientResponseException e) {
			throw new UserNotFoundException ("USER NOT FOUND");
		}
	}
	
	public Transfer[] viewPendingTransfers(Integer userId) throws TransferExceptions {
		Transfer[] allPendingTransfers = null;
		try {
			allPendingTransfers = restTemplate.exchange(BASE_URL + "/transfer/" + userId + "/pending-transfers", 
					HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
		} catch (RestClientResponseException e) {
			throw new TransferExceptions(e.getMessage());
		}
		return allPendingTransfers;
	}
	
	public void requestBucks(Transfer transfer) { //throws UserNotFoundException   {   
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity <Transfer> entity = new HttpEntity<>(transfer, headers);
		String url = BASE_URL + transfer.getFromUserId() + "/transfer/request-bucks";
//		try {
			restTemplate.postForObject(url, entity, Transfer.class);
//		} catch (RestClientResponseException e) {
//			throw new UserNotFoundException ("USER NOT FOUND");
//		}
	}


	private HttpEntity makeAuthEntity() {
		 HttpHeaders headers = new HttpHeaders();
	     headers.setBearerAuth(AUTH_TOKEN);
	     HttpEntity entity = new HttpEntity<>(headers);
	     return entity;
	}


	
	

	
}