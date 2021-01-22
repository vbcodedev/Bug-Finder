package com.example.bugfinder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class RecyclerItemActivity extends AppCompatActivity {
    private TextView mTitleTV;
    private TextView mDescTV;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_item);
        mTitleTV = findViewById(R.id.titleTV);
        mDescTV = findViewById(R.id.descTV);
        videoView = findViewById(R.id.videoView);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        String mTitle = intent.getStringExtra("iTitle");
        String mDesc = intent.getStringExtra("iDesc");
        String vidURI = intent.getStringExtra("iVideoURI");

        actionBar.setTitle(mTitle);

        mTitleTV.setText(mTitle);
        mDescTV.setText(mDesc);
        System.out.println(vidURI);
        if(vidURI.equals("null")) {
            videoView.setVisibility(View.INVISIBLE);
        } else {
            try {
                MediaController mediaController = new MediaController(this);
                videoView.setMediaController(mediaController);
                mediaController.setAnchorView(videoView);
                videoView.setVideoURI(Uri.parse(vidURI));
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    videoView.start();
                }
            });
        }
    }
}