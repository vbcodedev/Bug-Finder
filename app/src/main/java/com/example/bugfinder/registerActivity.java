package com.example.bugfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText regEmailET;
    private EditText regPasswordET;
    private EditText confirmPasswordET;
    private Button regButton;
    private TextView goToLoginTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        regEmailET = findViewById(R.id.regEmailET);
        regPasswordET = findViewById(R.id.regPwrdET);
        confirmPasswordET = findViewById(R.id.confPwrdET);
        regButton = findViewById(R.id.regBtn);
        goToLoginTV = findViewById(R.id.goToLoginTextView);
        regButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                register();
            }
        });
        goToLoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToLoginIntent = new Intent(registerActivity.this, MainActivity.class);
                startActivity(goToLoginIntent);
            }
        });
    }

    private void register() {
        String email = regEmailET.getText().toString().trim();
        String password = regPasswordET.getText().toString().trim();
        String confPassword = confirmPasswordET.getText().toString().trim();
        if(email.isEmpty()) {
            regEmailET.setError("Email is required!");
            regEmailET.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            regPasswordET.setError("Password is required!");
            regPasswordET.requestFocus();
            return;
        }
        if(password.length() < 6) {
            regPasswordET.setError("Password must be minimum 6 characters long");
            regPasswordET.requestFocus();
            return;
        }
        if(confPassword.isEmpty()) {
            confirmPasswordET.setError("Password confirmation is required!");
            confirmPasswordET.requestFocus();
            return;
        }
        if(!password.equals(confPassword)) {
            confirmPasswordET.setError("Passwords do not match!");
            confirmPasswordET.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(registerActivity.this, "Authentication Successful!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.w("registerActivity", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(registerActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}