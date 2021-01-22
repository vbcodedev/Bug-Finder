package com.example.bugfinder;

public class Video {
    private String videoURI;

    public Video() {}

    public Video(String videoUri) {
        this.videoURI = videoUri;
    }

    public String getVideoURI() {
        return videoURI;
    }

    public void setVideoURI(String videoURI) {
        this.videoURI = videoURI;
    }
}
