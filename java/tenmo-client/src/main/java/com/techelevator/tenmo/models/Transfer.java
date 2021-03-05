package com.techelevator.tenmo.models;

public class Transfer {
	
	private Long transferId;
	private Long fromAccountId;
	private Long toAccountId;
	
	private Long fromUserId;
	private Long toUserId;
	private String fromUsername;
	private String toUsername;
	
	private Double amount;
	
	private String transferType;
	
	private String transferStatus;
	
	public Long getTransferId() {
		return transferId;
	}
	
	public void setTransferId (Long transferId) {
		this.transferId = transferId;
	}
	
	public Long getFromAccountId() {
		return fromAccountId;
	}
	
	public void setFromAccountId(Long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}
	
	public Long getToAccountId () {
		return toAccountId;
	}
	
	public void setToAccountId(Long toAccountId) {
		this.toAccountId = toAccountId;
	}
	
	public Long getFromUserId() {
		return fromUserId;
	}

	public Long getToUserId() {
		return toUserId;
	}

	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getToUsername() {
		return toUsername;
	}

	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}

}
