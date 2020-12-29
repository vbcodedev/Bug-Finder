package com.example.bugfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView registerHereTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize TextView object
        registerHereTextView = findViewById(R.id.regHereTextView);
        registerHereTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToRegIntent = new Intent(MainActivity.this, registerActivity.class);
                startActivity(goToRegIntent);
            }
        });
    }
}