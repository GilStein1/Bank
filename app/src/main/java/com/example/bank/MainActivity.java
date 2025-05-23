package com.example.bank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

	private FirebaseAuth mAuth;
	private EditText etEmail, etPassword;
	private ActivityResultLauncher<Intent> signUpActivityLauncher;
	private ActivityResultLauncher<Intent> bankActivityLauncher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeViews();
		this.mAuth = FirebaseAuth.getInstance();
		this.signUpActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::atReturnFromSignUp);
		this.bankActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::atReturnFromBank);
	}

	private void initializeViews() {
		etEmail = findViewById(R.id.etEmail);
		etPassword = findViewById(R.id.etPassword);
	}

	public void onStart() {
		super.onStart();
		FirebaseUser currentUser = mAuth.getCurrentUser();
		if (currentUser != null) {
			Intent intent = new Intent(this, BankActivity.class);
			bankActivityLauncher.launch(intent);
		}
	}

	private void atReturnFromBank(ActivityResult result) {
//		if (result.getResultCode() == RESULT_OK) {
//			mAuth.signOut();
//		}
	}

	private void atReturnFromSignUp(ActivityResult result) {
		switch (result.getResultCode()) {
			case RESULT_OK:
				getNewUserFromSignUpPage(result.getData());
				break;
			case RESULT_CANCELED:
				signUpCanceled();
				break;
		}
	}

	private void signUpCanceled() {
		Toast.makeText(MainActivity.this, "sign up was canceled", Toast.LENGTH_SHORT).show();
	}

	private void getNewUserFromSignUpPage(Intent returnedIntent) {
		String email = returnedIntent.getExtras().getString("userEmail");
		String password = returnedIntent.getExtras().getString("userPassword");
		etEmail.setText(email);
		etPassword.setText(password);
		onStart();
	}

	private void logUserIn(String email, String password) {
		mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
			if (task.isSuccessful()) {
				Toast.makeText(MainActivity.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
				onStart();
			} else {
				Toast.makeText(MainActivity.this, "Error while logging in", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void signUpByButton(View view) {
		Intent intent = new Intent(this, SignUpActivity.class);
		signUpActivityLauncher.launch(intent);
	}

	public void logInByButton(View view) {
		String email = etEmail.getText().toString();
		String password = etPassword.getText().toString();
		logUserIn(email, password);
	}

}