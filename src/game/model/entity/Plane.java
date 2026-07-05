package game.model.entity;

import game.util.AssetManager;

import java.awt.Color;
import java.awt.Graphics;

public class Plane {
    private int x;
    private int y;
    private int speed;
    private int lives;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;

    public Plane(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.speed = 5;
        this.lives = 3;
    }

    public void setMoveLeft(boolean moveLeft) { this.moveLeft = moveLeft; }
    public void setMoveRight(boolean moveRight) { this.moveRight = moveRight; }
    public void setMoveUp(boolean moveUp) { this.moveUp = moveUp; }
    public void setMoveDown(boolean moveDown) { this.moveDown = moveDown; }

    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }

    public int getX() { return x; }
    public int getY() { return y; }

    public void update() {
        if (moveLeft && x > 0) x -= speed;
        if (moveRight && x < 800 - 40) x += speed;
        if (moveUp && y > 0) y -= speed;
        if (moveDown && y < 600 - 60) y += speed;
    }

    public void draw(Graphics g) {
        if (AssetManager.defaultPlane != null) {
            g.drawImage(AssetManager.defaultPlane, x, y, 50, 50, null);
        } else {
            g.setColor(Color.CYAN);
            g.fillPolygon(new int[]{x + 25, x, x + 50}, new int[]{y, y + 50, y + 50}, 3);
        }
    }
}