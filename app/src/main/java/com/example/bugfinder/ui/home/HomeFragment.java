package com.example.bugfinder.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bugfinder.BugPost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bugfinder.DashboardActivity;
import com.example.bugfinder.R;
import com.example.bugfinder.registerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private Button submitBtn;
    private TextView bugTV;
    private Spinner gameSpinner;
    private EditText bugTitleET;
    private EditText bugDescriptionET;
    private Button uploadBugBtn;
    private DatabaseReference mDatabase;
    long maxID = 0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        submitBtn = root.findViewById(R.id.submitBugBtn);
        bugTV = root.findViewById(R.id.postBugTV);
        gameSpinner = root.findViewById(R.id.gameSpinner);
        ArrayList<String> games = new ArrayList<String>();
        games.add("Call of Duty: Warzone");
        games.add("Fortnite");
        games.add("PubG");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, games);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameSpinner.setAdapter(adapter);
        bugTitleET = root.findViewById(R.id.bugTitleET);
        bugDescriptionET = root.findViewById(R.id.bugDescET);
        uploadBugBtn = root.findViewById(R.id.uploadFileBtn);
        //uploadBugBtn.setVisibility(View.INVISIBLE);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    maxID = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToDatabase();
                bugTitleET.getText().clear();
                bugDescriptionET.getText().clear();
            }
        });

        return root;
    }

    private void saveToDatabase() {
        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = currUser.getEmail();
        String gameName = gameSpinner.getSelectedItem().toString();
        String bugTitle = bugTitleET.getText().toString().trim();
        String bugDescription = bugDescriptionET.getText().toString().trim();
        BugPost newPost = new BugPost(email, gameName, bugTitle, bugDescription);
        mDatabase.child(String.valueOf(maxID + 1)).setValue(newPost);
    }
}