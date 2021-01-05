package com.example.bugfinder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RecyclerItemActivity extends AppCompatActivity {
    private TextView mTitleTV;
    private TextView mDescTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_item);
        mTitleTV = findViewById(R.id.titleTV);
        mDescTV = findViewById(R.id.descTV);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        String mTitle = intent.getStringExtra("iTitle");
        String mDesc = intent.getStringExtra("iDesc");

        actionBar.setTitle(mTitle);

        mTitleTV.setText(mTitle);
        mDescTV.setText(mDesc);
    }
}