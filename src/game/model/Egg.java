package game.model;

import java.awt.Color;
import java.awt.Graphics;

public class Egg {
    private int x;
    private int y;
    private int speed;

    public Egg(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = 4;
    }

    public void update() {
        y += speed;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, 8, 12);
    }
}