package com.example.bank;

import android.widget.Toast;

import java.util.List;

public class Utils {

	public static Account getAccountFromNumber(String accountNumber, List<Account> accounts) {
		for (Account account : accounts) {
			if (account.getNumber().equals(accountNumber)) {
				return account;
			}
		}
		return null;
	}

	public static boolean isAccountToTransferReal(String number, List<Account> accounts, Toast errorMessage) {
		boolean isReal = false;
		for (Account account : accounts) {
			isReal |= account.getNumber().equals(number);
		}
		if (!isReal) {
			errorMessage.show();
		}
		return isReal;
	}

	public static boolean isAmountANumber(String amount, Toast errorMessage) {
		boolean isANumber = true;
		for (int i = 0; i < amount.length(); i++) {
			isANumber &= isCharANumber(amount.charAt(i));
		}
		if (!isANumber) {
			errorMessage.show();
		}
		return isANumber;
	}

	public static boolean isCharANumber(char c) {
		boolean isANumber = false;
		for (int i = 0; i < 10; i++) {
			isANumber |= c == String.valueOf(i).charAt(0);
		}
		return isANumber;
	}

}
