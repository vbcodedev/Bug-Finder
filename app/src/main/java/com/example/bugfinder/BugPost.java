package com.example.bugfinder;

public class BugPost {
    private String userEmail;
    private String game;
    private String bugTitle;
    private String bugDescription;

    public BugPost(){}
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

    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public void setGame(String game) { this.game = game; }
    public void setBugTitle(String bugTitle) { this.bugTitle = bugTitle; }
    public void setBugDescription(String bugDescription) { this.bugDescription = bugDescription; }
}
