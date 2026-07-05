package game.model;

import java.awt.Color;
import java.awt.Graphics;

public class Explosion {
    private int x;
    private int y;
    private int lifeTimer;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
        this.lifeTimer = 15;
    }

    public void update() {
        lifeTimer--;
    }

    public boolean isDead() {
        return lifeTimer <= 0;
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        int size = 30 + (15 - lifeTimer) * 2;
        int drawX = x - (15 - lifeTimer);
        int drawY = y - (15 - lifeTimer);
        g.fillOval(drawX, drawY, size, size);
    }
}