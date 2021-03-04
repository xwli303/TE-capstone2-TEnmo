package com.techelevator.tenmo.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcAccountDAO implements AccountDAO {
	
	 private JdbcTemplate jdbcTemplate;
	 
	 public JdbcAccountDAO(JdbcTemplate jdbcTemplate) {
	        this.jdbcTemplate = jdbcTemplate;
	    }

	@Override
	public double getBalance(Long userId) {
		String sql = "SELECT balance FROM accounts " + 
				"WHERE user_id = ?";
		try {
			double balance = jdbcTemplate.queryForObject(sql, Double.class, userId);
			return balance;
		} catch (DataAccessException e) {
		return 0;
	}
	}
	
}
