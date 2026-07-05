package game.model;

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

    public void update() {
        if (moveLeft && x > 0) x -= speed;
        if (moveRight && x < 800 - 40) x += speed;
        if (moveUp && y > 0) y -= speed;
        if (moveDown && y < 600 - 60) y += speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillPolygon(
                new int[]{x + 20, x, x + 40},
                new int[]{y, y + 40, y + 40},
                3
        );
    }
}