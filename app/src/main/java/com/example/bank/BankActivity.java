package com.example.bank;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BankActivity extends AppCompatActivity {

	private TextView tvAccountNum, tvAccountBalance;
	private EditText etAmountOfDeposit;
	private Account currentAccount;
	private FirebaseAuth mAuth;
	private DatabaseReference accountRef;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bank);
		mAuth = FirebaseAuth.getInstance();
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		accountRef = database.getReference("accounts");
		initView();
	}

	private void initView() {
		tvAccountNum = findViewById(R.id.tvAccountNum);
		tvAccountBalance = findViewById(R.id.tvAccountBalance);
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.deposit_dialog);
		dialog.setTitle("deposit money");
		dialog.setCancelable(false);
		etAmountOfDeposit = dialog.findViewById(R.id.etAmountOfDeposit);
	}

	@Override
	public void onStart() {
		super.onStart();
		findCurrentAccount();
	}

	private void updateAccountValuesInView() {
		tvAccountNum.setText("Account: " + currentAccount.getNumber());
		tvAccountBalance.setText("Balance: " + currentAccount.getBalance());
	}

	public void depositMoney(View view) {
		dialog.show();
	}

	public void cancelDeposit(View view) {
		dialog.cancel();
	}

	public void finishDeposit(View view) {
		if (Utils.isAmountANumber(etAmountOfDeposit.getText().toString(), Toast.makeText(this, "invalid amount to deposit", Toast.LENGTH_SHORT))) {
			currentAccount.setBalance(currentAccount.getBalance() + Integer.parseInt(etAmountOfDeposit.getText().toString()));
			accountRef.child(currentAccount.getUserId()).setValue(currentAccount);
			Toast.makeText(this, "successfully deposited money", Toast.LENGTH_SHORT).show();
			dialog.cancel();
		}
	}

	public void transferMoney(View view) {
		Intent intent = new Intent(this, MoneyTransferActivity.class);
		startActivity(intent);
	}

	public void logOut(View view) {
		mAuth.signOut();
		setResult(RESULT_OK);
		finish();
	}

	public void findCurrentAccount() {
		accountRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot data : dataSnapshot.getChildren()) {
					Account account = data.getValue(Account.class);
					if (mAuth.getCurrentUser() != null && account.getAccountId().equals(mAuth.getCurrentUser().getUid())) {
						currentAccount = account;
						updateAccountValuesInView();
					}
				}
			}

			@Override
			public void onCancelled(DatabaseError ignored) {
			}
		});
	}

}