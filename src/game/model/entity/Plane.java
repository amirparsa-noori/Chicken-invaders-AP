package game.model.entity;

import game.util.AssetManager;
import java.awt.Graphics;

public class Plane {
    private int x;
    private int y;
    private int speed;
    private int lives;
    private int type;
    private int fireRate;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;

    public Plane(int startX, int startY, int type) {
        this.x = startX;
        this.y = startY;
        this.type = type;
        if (type == 2) {
            this.speed = 7;
            this.lives = 3;
            this.fireRate = 250;
        } else if (type == 3) {
            this.speed = 4;
            this.lives = 5;
            this.fireRate = 200;
        } else if (type == 4) {
            this.speed = 5;
            this.lives = 3;
            this.fireRate = 150;
        } else {
            this.speed = 5;
            this.lives = 3;
            this.fireRate = 300;
        }
    }

    public void setMoveLeft(boolean moveLeft) { this.moveLeft = moveLeft; }
    public void setMoveRight(boolean moveRight) { this.moveRight = moveRight; }
    public void setMoveUp(boolean moveUp) { this.moveUp = moveUp; }
    public void setMoveDown(boolean moveDown) { this.moveDown = moveDown; }
    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getFireRate() { return fireRate; }
    public int getType() { return type; }

    public void update() {
        if (moveLeft && x > 0) x -= speed;
        if (moveRight && x < 800 - 50) x += speed;
        if (moveUp && y > 0) y -= speed;
        if (moveDown && y < 600 - 50) y += speed;
    }

    public void draw(Graphics g) {
        int index = type - 1;
        if (index >= 0 && index < 7 && AssetManager.planes[index] != null) {
            g.drawImage(AssetManager.planes[index], x, y, 50, 50, null);
        }
    }
}