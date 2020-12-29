package com.example.bugfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class registerActivity extends AppCompatActivity {

    private EditText regEmailET;
    private EditText regPasswordET;
    private EditText confirmPasswordET;
    private Button regButton;
    private TextView goToLoginTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        goToLoginTV = findViewById(R.id.goToLoginTextView);
        goToLoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToLoginIntent = new Intent(registerActivity.this, MainActivity.class);
                startActivity(goToLoginIntent);
            }
        });
    }
}