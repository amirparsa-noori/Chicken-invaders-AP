package game.model;

public class User {
    private String username;
    private int highestScore;
    private int lastLevel;

    public User(String username, int highestScore, int lastLevel) {
        this.username = username;
        this.highestScore = highestScore;
        this.lastLevel = lastLevel;
    }

    public String getUsername() { return username; }
    public int getHighestScore() { return highestScore; }
    public int getLastLevel() { return lastLevel; }
}