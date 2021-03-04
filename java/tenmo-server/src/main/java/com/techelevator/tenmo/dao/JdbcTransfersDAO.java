package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Transfer;
@Component
public class JdbcTransfersDAO implements TransfersDAO{
	
	private JdbcTemplate jdbcTemplate;
	private JdbcUserDAO userDAO;
	private static final int TRANSFER_TYPE_ID_SEND = 2;
	
	
	
	public JdbcTransfersDAO (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Double viewBalance(Long userId) {
		String sql = "SELECT balance FROM accounts WHERE user_id = ?";
		
		try {
			double balance = jdbcTemplate.queryForObject(sql, Double.class, userId);
			return balance;
		} catch (DataAccessException e) {
			return 0.0;
		}
		
	}

	@Override
	public Double sendTransfer(Long userId, String userName) {
		//list all users 
//		userDAO.listUsersForTransfer(); //returns all users id and usernames
		/*
		 * two parts with sql 
		 * 1. insert transfer record into transfers table
		 * 2. update the balances for sender and user
		 */
		double transferAmount; 
		
		String sql ;

		
		
		
		return null; //return remaining balance?
	}
	
	@Override
	public List<Transfer> viewTransfers(Long userId) {  //do we need to pass it through id?
		// TODO Auto-generated method stub
		List<Transfer> transfersList = new ArrayList<>();
		
		String sql = "SELECT transfer_id, amount, transfer_type_desc, username AS From, " + 
				"(SELECT username FROM users WHERE user_id = " + 
				"(SELECT user_id FROM accounts WHERE account_id=t.account_to)) AS To FROM  users " + 
				"JOIN accounts a ON a.user_id = u.user_id " + 
				"JOIN transfers t ON t.account_from = a.account_id " + 
				"JOIN transfer_types ty ON ty.transfer_type_id = t.transfer_type_id " + 
				"WHERE u.user_id= ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
		while (result.next()) {
			Transfer transfer = mapTransferDetails(result);
			transfersList.add(transfer);		
		}
		return transfersList;
	}

	@Override
	public Transfer viewTransferDetails(Long transferId) {
		String sql = "SELECT transfer_id, amount, transfer_type_desc, username AS From, " + 
				"(SELECT username FROM users WHERE user_id = " + 
				"(SELECT user_id FROM accounts WHERE account_id=t.account_to)) AS To FROM  users " + 
				"JOIN accounts a ON a.user_id = u.user_id " + 
				"JOIN transfers t ON t.account_from = a.account_id " + 
				"JOIN transfer_types ty ON ty.transfer_type_id = t.transfer_type_id " + 
				"WHERE t.tranfer_id= ?";  //transfer id 
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
		
		if(result != null) {
			if (result.next()) {
				Transfer transfer = mapTransferDetails(result);
				return transfer;
			}
		}
		return null;
	}

	
	//maptorowset
	private Transfer mapRowToTansfers(SqlRowSet rowset) {
		Transfer transfer = new Transfer();
		transfer.setTransferId(rowset.getLong("transfer_id"));
		transfer.setFromAccountId(rowset.getLong("account_from"));
		transfer.setToAccountId(rowset.getLong("account_to"));
		transfer.setAmount(rowset.getDouble("amount"));
		return transfer;
	
	}
	
	private Transfer mapTransferDetails(SqlRowSet rowset) {
		Transfer transfer = new Transfer();
		transfer.setTransferId(rowset.getLong("transfer_id"));
		transfer.setAmount(rowset.getDouble("amount"));
		transfer.setFromUsername(rowset.getString("from"));
		transfer.setToUsername(rowset.getString("to"));
		transfer.setAmount(rowset.getDouble("amount"));
		transfer.setTransferType(rowset.getString("transfer_type_desc"));
		return transfer;
	}
	
	
	//additional methods
//	@Override
//	public List<Transfers> viewPendingRequests() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void requestTransfers(Long accountId) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean approveOrDenyTransfers() {
//		// TODO Auto-generated method stub
//		return false;
//	}

}