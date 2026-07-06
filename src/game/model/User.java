package game.model;

public class User {
    private String username;
    private int highestScore;
    private int lastLevel;
    private int activePlane;

    public User(String username, int highestScore, int lastLevel, int activePlane) {
        this.username = username;
        this.highestScore = highestScore;
        this.lastLevel = lastLevel;
        this.activePlane = activePlane;
    }

    public String getUsername() { return username; }
    public int getHighestScore() { return highestScore; }
    public void setHighestScore(int highestScore) { this.highestScore = highestScore; }
    public int getLastLevel() { return lastLevel; }
    public int getActivePlane() { return activePlane; }
    public void setActivePlane(int activePlane) { this.activePlane = activePlane; }
}