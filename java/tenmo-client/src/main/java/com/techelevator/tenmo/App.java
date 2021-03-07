package com.techelevator.tenmo;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.InsufficientFundsException;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferExceptions;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.UserNotFoundException;
import com.techelevator.tenmo.services.UserService;
import com.techelevator.view.ConsoleService;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private UserService userService;
    private User user;
    private Transfer transfer;
    
    
    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), 
    			new UserService (API_BASE_URL));
    	app.run();
    	
    }

    public App(ConsoleService console, AuthenticationService authenticationService, UserService userService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.userService = userService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				try {
					viewCurrentBalance();
				} catch (UserNotFoundException e) {
					System.out.println("USER NOT FOUND");
				}
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				
						try {
							viewTransferHistory();
						} catch (TransferExceptions e) {
							e.getMessage();
						}
					
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				try {
					sendBucks();
				} catch (UserNotFoundException e) {
					System.out.println("INVALID USER ID");
				}
					
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				try {
					requestBucks();
				} catch (UserNotFoundException e) {
					System.out.println("INVALID USER ID");;
				}
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() throws UserNotFoundException {		
		Integer userId = currentUser.getUser().getId();
		Double balance;
		balance = userService.getAccountBalance(userId);
		
		System.out.println("Your current account balance is: " + balance);
	}

	private void viewTransferHistory() throws TransferExceptions  {	
		Integer userId = currentUser.getUser().getId();
		Transfer[] allTransfers;
		try {
			allTransfers = userService.allTransfers(userId);
		
			if (allTransfers.length != 0) {
				System.out.println("------------------------------------------------");
				System.out.printf("%-15s %-10s %-10s %10s %n", "Transfer ID", "From", "To", "Amount");
				System.out.println("------------------------------------------------");
				for (Transfer eachTransfer : allTransfers) {
					System.out.printf("%-15s %-10s %-10s %10s %n", eachTransfer.getTransferId(), 
							eachTransfer.getFromUsername(), eachTransfer.getToUsername(), eachTransfer.getAmount());
				}
			}
			else {
				int UserResponse = console.getUserInputInteger("No transfer history.  Press 0 to return to Main Menu");
			 if (UserResponse == 0) {
				mainMenu(); 
			 }
			}
		} catch (TransferExceptions e) {
			System.out.println("INVALID USER ID");;
		}
		
		Integer requestTransferId = 
				console.getUserInputInteger("Select transfer ID to view Transfer details or 0 to return to main menu: ");
		if (requestTransferId == 0){
			mainMenu();
		}
		if (requestTransferId != null) {
			Transfer transfer;
			try {
				transfer = userService.transferDetails(userId, requestTransferId);
				System.out.println("-----------------------------------");
				System.out.println("Transfer Details");
				System.out.println("-----------------------------------");
				System.out.printf("%-10s %-10s %n", "ID: ", transfer.getTransferId());
				System.out.printf("%-10s %-10s %n", "From: ", transfer.getFromUsername());
				System.out.printf("%-10s %-10s %n", "To: ", transfer.getToUsername());
				System.out.printf("%-10s %-10s %n", "Type: ",transfer.getTransferType());
				System.out.printf("%-10s %-10s %n", "Status: ",transfer.getTransferStatus());
				System.out.printf("%-10s %-10s %n", "Amount: ",transfer.getAmount());
			
			} catch (NullPointerException e) {
				System.out.println("INVALID TRANSFER ID");
			}
		}
		
	}

	private void viewPendingRequests() {
		Integer userId = currentUser.getUser().getId();
		Transfer[] pendingTransfers;
		try {
			pendingTransfers = userService.viewPendingTransfers(userId);
			if (pendingTransfers.length != 0) {
				System.out.println("--------------------------------------------");
				System.out.println("Pending Transfers");
				System.out.println("--------------------------------------------");
				System.out.printf("%-15s %-10s %10s %n", "Pending ID", "To", "Amount");
		
				for (Transfer eachTransfer : pendingTransfers) {
					System.out.printf("%-15s %-10s %10s %n", eachTransfer.getTransferId(), 
							 eachTransfer.getToUsername(), eachTransfer.getAmount());
				}
			}
			else {
				int UserResponse = console.getUserInputInteger("No transfer history.  Press 0 to return to Main Menu");
			 if (UserResponse == 0) {
				mainMenu(); 
			 }
			}
		} catch (TransferExceptions e) {
			e.getMessage();;
		}
		
	}

	private void sendBucks() throws UserNotFoundException  {
		printAllUsers();  //i put this method after requestbucks
			
		Transfer transferReq = new Transfer();//make a new tranfer object to store values
		Integer receiverId = console.getUserInputInteger("Select User Id to send money, or 0 to return to main menu");
		if (receiverId == 0) {
			mainMenu();
		}
		
		Integer senderUserId = currentUser.getUser().getId();
		Double amount = console.getUserInputDouble("How much do you want to send?");
		//getting user inputs and store them as variables 
		transferReq.setFromUserId(senderUserId);
		transferReq.setToUserId(receiverId);
		transferReq.setAmount(amount);
		
		Integer userId = currentUser.getUser().getId();
		Double balance = userService.getAccountBalance(userId);
		
		if (balance >= amount) {
			userService.sendBucks(transferReq);
		} else {
		System.out.println("Insufficient Funds.  Choose a different amount or press 0 to return to main menu");
		}
		if (receiverId == 0) {
			mainMenu();
		
		}
	}

	private void requestBucks() throws UserNotFoundException {//throws UserNotFoundException {
		printAllUsers();
		Transfer transferReq = new Transfer();
		Integer receiverId = console.getUserInputInteger("Select User Id to request money, or 0 to return to main menu");
		if (receiverId == 0) {
			mainMenu();
		}
		
		Integer senderUserId = currentUser.getUser().getId();
		Double amount = console.getUserInputDouble("How much are you requesting?");
		transferReq.setFromUserId(senderUserId);
		transferReq.setToUserId(receiverId);
		transferReq.setAmount(amount);
	
		userService.requestBucks(transferReq);
		
	}
	
	public void printAllUsers() throws UserNotFoundException {
		User[] user = userService.getAllUsers();
		
		System.out.printf("%-10s %-5s %n", "User Id", "Username");
		System.out.println("-----------------------------------");
		for (User eachUser : user) {
			if (!eachUser.getId().equals(currentUser.getUser().getId())) {
				System.out.printf("%-10s %-5s %n", eachUser.getId(), eachUser.getUsername());
			}			
		}
		System.out.println("-----------------------------------");
	}
	
	
	
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
