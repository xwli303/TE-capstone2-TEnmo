package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Account;
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
	public Double viewBalance(Long id) {  //userid
		String sql = "SELECT balance FROM accounts WHERE user_id = ?";
		
		try {
			double balance = jdbcTemplate.queryForObject(sql, Double.class, id);
			return balance;
		} catch (DataAccessException e) {
			return 0.0;
		}
		
	}

	//create a transfer
	@Override
	public void create(Long userId, Long receiverId, Double amount) {
		//account from
		String sqlFrom = "UPDATE accounts SET balance = (balance - ?) WHERE user_id = ?";
		jdbcTemplate.update(sqlFrom,  amount, userId);
		//account to
		String sqlTo = "UPDATE accounts SET balance = (balance + ?) WHERE user_id = ?";
		jdbcTemplate.update(sqlTo,  amount, receiverId);		
		//inserting into the transfer table
		String sqlTransfer = "INSERT INTO transfers (transfer_type_id, transfer_status_id, "
				+ "account_from, account_to, amount) " + 
				"VALUES (2, 2, (SELECT account_id FROM accounts WHERE user_id = ?), "
				+ "(SELECT account_id FROM accounts WHERE user_id = ?), ?)";
		jdbcTemplate.update(sqlTransfer, userId, receiverId, amount);
		
	}
	
	@Override
	public List<Transfer> viewTransfers(Long userId) { 
		List<Transfer> transfersList = new ArrayList<>();
		
		String sql = "SELECT transfer_id, amount, transfer_type_desc, username AS From, "
				+ "(SELECT username FROM users WHERE user_id =(SELECT user_id FROM accounts WHERE account_id=t.account_to)) "
				+ "AS To FROM  users u "
				+ "JOIN accounts a ON a.user_id = u.user_id " 
				+ "JOIN transfers t ON t.account_from = a.account_id " 
				+ "JOIN transfer_types ty ON ty.transfer_type_id = t.transfer_type_id "
				+ "WHERE u.user_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
		while (result.next()) {
			Transfer transfer = mapRowToTansfers(result);
			transfersList.add(transfer);		
		}
		return transfersList;
	}

	
	//this query is a little different because this one shows status
	@Override
	public Transfer viewTransferDetails(Long transferId) {
		String sql = 
				"SELECT transfer_id, amount, transfer_type_desc, transfer_status_desc, username AS From, "
				+ "(SELECT username FROM users WHERE user_id = "
				+ "(SELECT user_id FROM accounts WHERE account_id=t.account_to)) "
				+ "AS To FROM  users u JOIN accounts a ON a.user_id = u.user_id "
				+ "JOIN transfers t ON t.account_from = a.account_id "
				+ "JOIN transfer_types ty ON ty.transfer_type_id = t.transfer_type_id "
				+ "JOIN transfer_statuses ts ON ts.transfer_status_id = t.transfer_status_id "
				+ "WHERE t.transfer_id = ?";
				
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
		
		if(result != null) {
			if (result.next()) {
				Transfer transfer = mapTransferDetails(result);
				return transfer;
			}
		}
		return null;
	}
	
	//additional - view pending
	//additional methods
	@Override
	public List<Transfer> viewPendingRequests(Long userId) {
		List<Transfer> pendingTransfers = new ArrayList<>();
		String sql =
				"SELECT transfer_id, amount, transfer_type_desc, "
				+ "(SELECT username FROM users WHERE user_id = "
				+ "(SELECT user_id FROM accounts WHERE account_id=t.account_to)) AS To FROM users u "
				+ "JOIN accounts a ON a.user_id = u.user_id "
				+ "JOIN transfers t ON t.account_from = a.account_id "
				+ "JOIN transfer_types ty ON ty.transfer_type_id = t.transfer_type_id "
				+ "JOIN transfer_statuses ts ON ts.transfer_status_id = t.transfer_status_id "
				+ "WHERE u.user_id = ? and ts.transfer_status_desc = 'Pending'";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
		
			while(result.next()) {
				Transfer transfer = new Transfer();
				transfer.setTransferId(result.getLong("transfer_id"));
				transfer.setAmount(result.getDouble("amount"));
				transfer.setToUsername(result.getString("to"));
				transfer.setTransferType(result.getString("transfer_type_desc"));
				pendingTransfers.add(transfer);
			}
			return pendingTransfers;
		
	}
	
	public void requestBucks(Long userId, Long receiverId, Double amount) {
	String sqlRequest = "INSERT INTO transfers (transfer_type_id, transfer_status_id, "
			+ "account_from, account_to, amount) VALUES "
			+ "(1, 1, (SELECT account_id FROM accounts WHERE user_id = ?),"
			+ "(SELECT account_id FROM accounts WHERE user_id = ?), ?)";	
	jdbcTemplate.update(sqlRequest, userId, receiverId, amount);
		
	}

	
	//maptorowset
	private Transfer mapRowToTansfers(SqlRowSet rowset) {
		Transfer transfer = new Transfer();
		transfer.setTransferId(rowset.getLong("transfer_id"));
		transfer.setAmount(rowset.getDouble("amount"));
		transfer.setFromUsername(rowset.getString("from"));
		transfer.setToUsername(rowset.getString("to"));
		transfer.setAmount(rowset.getDouble("amount"));
		transfer.setTransferType(rowset.getString("transfer_type_desc"));
		return transfer;
	
	}
	
	//this differs from the map method above slightly because
	//this one has the transfer status (for transfer details)
	private Transfer mapTransferDetails(SqlRowSet rowset) {
		Transfer transfer = new Transfer();
		transfer.setTransferId(rowset.getLong("transfer_id"));
		transfer.setAmount(rowset.getDouble("amount"));
		transfer.setFromUsername(rowset.getString("from"));
		transfer.setToUsername(rowset.getString("to"));
		transfer.setAmount(rowset.getDouble("amount"));
		transfer.setTransferType(rowset.getString("transfer_type_desc"));
		transfer.setTransferStatus(rowset.getString("transfer_status_desc"));
		
		return transfer;
	}




//additionals
	

//	@Override
//	public boolean approveOrDenyTransfers() {
//		// TODO Auto-generated method stub
//		return false;
//	}

}
