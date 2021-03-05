package com.techelevator.tenmo.models;

public class Transfer {
	
	private Integer transferId;
	private Integer fromAccountId;
	private Integer toAccountId;
	
	private Integer fromUserId;
	private Integer toUserId;
	private String fromUsername;
	private String toUsername;
	
	private Double amount;
	
	private String transferType;
	
	private String transferStatus;
	
	public Integer getTransferId() {
		return transferId;
	}
	
	public void setTransferId (Integer transferId) {
		this.transferId = transferId;
	}
	
	public Integer getFromAccountId() {
		return fromAccountId;
	}
	
	public void setFromAccountId(Integer fromAccountId) {
		this.fromAccountId = fromAccountId;
	}
	
	public Integer getToAccountId () {
		return toAccountId;
	}
	
	public void setToAccountId(Integer toAccountId) {
		this.toAccountId = toAccountId;
	}
	
	public Integer getFromUserId() {
		return fromUserId;
	}
	
	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(Integer toUserId) {
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
