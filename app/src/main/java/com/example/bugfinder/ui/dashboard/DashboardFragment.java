package com.example.bugfinder.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bugfinder.BugPost;
import com.example.bugfinder.Model;
import com.example.bugfinder.MyAdapter;
import com.example.bugfinder.R;
import com.example.bugfinder.Video;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    private Spinner searchGameSpinner;
    private DatabaseReference mDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");

        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = mDatabase.orderByChild("game").equalTo("Call of Duty: Warzone");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Model> models = new ArrayList<Model>();
                MyAdapter newAdapter;
                for(DataSnapshot post : snapshot.getChildren()) {
                    BugPost currPost = post.getValue(BugPost.class);
                    Video currVideo = post.child("video").getValue(Video.class);
                    Model m = new Model();
                    m.setTitle(currPost.getBugTitle());
                    m.setDescription(currPost.getBugDescription());
                    m.setVideoURI(currVideo.getVideoURI());
                    models.add(m);
                }
                newAdapter = new MyAdapter(getActivity(), models);
                mRecyclerView.swapAdapter(newAdapter, false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        searchGameSpinner = root.findViewById(R.id.searchGameSpinner);
        ArrayList<String> games = new ArrayList<String>();
        games.add("Call of Duty: Warzone");
        games.add("Fortnite");
        games.add("PubG");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, games);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchGameSpinner.setAdapter(adapter);

        searchGameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Query query = mDatabase.orderByChild("game").equalTo(searchGameSpinner.getSelectedItem().toString());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Model> models = new ArrayList<Model>();
                        for(DataSnapshot post : snapshot.getChildren()) {
                            BugPost currPost = post.getValue(BugPost.class);
                            Video currVideo = post.child("video").getValue(Video.class);
                            Model m = new Model();
                            m.setTitle(currPost.getBugTitle());
                            m.setDescription(currPost.getBugDescription());
                            m.setVideoURI(currVideo.getVideoURI());
                            models.add(m);
                        }
                        myAdapter = new MyAdapter(getActivity(), models);
                        mRecyclerView.setAdapter(myAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        return root;
    }
}