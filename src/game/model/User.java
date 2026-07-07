package game.model;

public class User {
    private String username;
    private int highestScore;
    private int lastLevel;
    private int activePlane;

    public boolean sMusic, sShoot, sExplode, sGameOver;

    public User(String username, int highestScore, int lastLevel, int activePlane,
                boolean sMusic, boolean sShoot, boolean sExplode, boolean sGameOver) {
        this.username = username;
        this.highestScore = highestScore;
        this.lastLevel = lastLevel;
        this.activePlane = activePlane;
        this.sMusic = sMusic;
        this.sShoot = sShoot;
        this.sExplode = sExplode;
        this.sGameOver = sGameOver;
    }

    public String getUsername() { return username; }
    public int getHighestScore() { return highestScore; }
    public void setHighestScore(int highestScore) { this.highestScore = highestScore; }
    public int getLastLevel() { return lastLevel; }
    public int getActivePlane() { return activePlane; }
    public void setActivePlane(int activePlane) { this.activePlane = activePlane; }
}