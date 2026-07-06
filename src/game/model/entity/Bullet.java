package game.model.entity;

import game.util.AssetManager;

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
        if (AssetManager.bullet != null) {
            g.drawImage(AssetManager.bullet, x, y - 10, 15, 30, null);
        } else {
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, 4, 15);
            // بعضی اوقات اَسِت ها لود نمیشه برای همین من اینو اضافه کردم که اگر لود نشد یه شکل باشه که خالی نباشه !!
        }
    }
}