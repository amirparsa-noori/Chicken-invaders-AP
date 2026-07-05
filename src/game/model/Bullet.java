package game.model;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
    private int x;
    private int y;
    private int speed;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = 10;
    }

    public void update() {
        y -= speed;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 4, 15);
    }
}