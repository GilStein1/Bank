package com.example.bank.gilBase;

public class ServerIsOfflineException extends RuntimeException {
	public ServerIsOfflineException(String message) {
		super(message);
	}
}
