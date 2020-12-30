package com.example.bugfinder;

public class BugPost {
    private String userEmail;
    private String game;
    private String bugTitle;
    private String bugDescription;

    public BugPost(String posterEmail, String gameName, String bugTitle, String bugDesc) {
        this.userEmail = posterEmail;
        this.game = gameName;
        this.bugTitle = bugTitle;
        this.bugDescription = bugDesc;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getGame() {
        return game;
    }

    public String getBugTitle() {
        return bugTitle;
    }

    public String getBugDescription() {
        return bugDescription;
    }
}
