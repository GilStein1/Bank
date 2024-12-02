package com.example.bank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MoneyTransferActivity extends AppCompatActivity {

	private EditText etAmount, etAccountNumber;
	private FirebaseAuth mAuth;
	private DatabaseReference accountRef;
	private List<Account> accounts;
	private Account currentAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_money_transfer);
		initViews();
		mAuth = FirebaseAuth.getInstance();
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		accountRef = database.getReference("accounts");
	}

	@Override
	public void onStart() {
		super.onStart();
		getAllAccounts();
	}

	private void initViews() {
		etAmount = findViewById(R.id.etAmount);
		etAccountNumber = findViewById(R.id.etAccountNumber);
	}

	public void transferMoney(View view) {
		if (Utils.isAccountToTransferReal(etAccountNumber.getText().toString(), accounts, Toast.makeText(MoneyTransferActivity.this, "Please enter a real account number", Toast.LENGTH_SHORT)) & Utils.isAmountANumber(etAmount.getText().toString(), Toast.makeText(MoneyTransferActivity.this, "Please enter a real amount to transfer", Toast.LENGTH_SHORT))) {
			Account accountToTransferTo = Utils.getAccountFromNumber(etAccountNumber.getText().toString(), accounts);
			int amountToTransfer = Integer.parseInt(etAmount.getText().toString());
			accountToTransferTo.setBalance(accountToTransferTo.getBalance() + amountToTransfer);
			currentAccount.setBalance(currentAccount.getBalance() - amountToTransfer);
			accountRef.child(accountToTransferTo.getUserId()).setValue(accountToTransferTo);
			accountRef.child(currentAccount.getUserId()).setValue(currentAccount);
			Toast.makeText(MoneyTransferActivity.this, "Money was transferred successfully", Toast.LENGTH_SHORT).show();
		}
	}

	public void getAllAccounts() {
		accounts = new ArrayList<>();
		accountRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot data : dataSnapshot.getChildren()) {
					Account account = data.getValue(Account.class);
					accounts.add(account);
					if (account.getAccountId().equals(mAuth.getCurrentUser().getUid())) {
						currentAccount = account;
					}
				}
			}

			@Override
			public void onCancelled(DatabaseError ignored) {
			}
		});
	}

	public void backToBankPage(View view) {
		Intent intent = new Intent(this, BankActivity.class);
		startActivity(intent);
	}

}