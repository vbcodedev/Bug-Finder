package com.example.bugfinder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder {
    TextView mTitle, mDesc;
    public MyHolder(@NonNull View itemView) {
        super(itemView);
        this.mTitle = itemView.findViewById(R.id.titleTextView);
        this.mDesc = itemView.findViewById(R.id.descTextView);
    }
}
