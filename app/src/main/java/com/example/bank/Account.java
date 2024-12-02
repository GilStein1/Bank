package com.example.bank;

public class Account {

	private String accountId;
	private String name;
	private String number;
	private String userId;
	private double balance;

	public Account(String userId, String number, String name, String accountId) {
		this.userId = userId;
		this.number = number;
		this.name = name;
		this.accountId = accountId;
		this.balance = 0;
	}

	public Account() {
		this.accountId = "";
		this.name = "";
		this.number = "";
		this.userId = "";
		this.balance = 0;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
