package com.example.bugfinder.ui.home;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
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
import com.example.bugfinder.MainActivity;
import com.example.bugfinder.R;
import com.example.bugfinder.Video;
import com.example.bugfinder.registerActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    private static final int PICK_VIDEO_REQ = 1;
    private Button submitBtn;
    private Button clearBtn;
    private TextView bugTV;
    private Uri videoURI;
    private Spinner gameSpinner;
    private EditText bugTitleET;
    private EditText bugDescriptionET;
    private Button uploadBugBtn;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    long maxID = 0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        clearBtn = root.findViewById(R.id.clearBtn);
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
        mStorage = FirebaseStorage.getInstance().getReference("videos");
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
                clearInputs();
            }
        });

        uploadBugBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectVideo();
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearInputs();
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
        String id = String.valueOf(maxID + 1);
        mDatabase.child(id).setValue(newPost);
        uploadVideo(id);
    }

    private void selectVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_VIDEO_REQ);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_VIDEO_REQ && resultCode == RESULT_OK && data != null && data.getData() != null);
        videoURI = data.getData();
    }

    private String getFileExt(Uri videoUri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(videoUri));
    }

    private void uploadVideo(String id) {
        if(videoURI != null) {
            StorageReference reference = mStorage.child(System.currentTimeMillis() + "." + getFileExt(videoURI));
            reference.putFile(videoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getActivity(), "Upload Successful.", Toast.LENGTH_SHORT).show();
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Video video =  new Video(uri.toString());
                            mDatabase.child(id).child("video").setValue(video);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "FAILED TO GET DOWNLOAD URL", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Upload Failed.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mDatabase.child(id).child("video").setValue(new Video("null"));
        }
    }

    private void clearInputs() {
        bugTitleET.getText().clear();
        bugDescriptionET.getText().clear();
    }
}