package game.model.entity;

import game.util.AssetManager;
import java.awt.Color;
import java.awt.Graphics;

public class Egg {
    private double x;
    private double y;
    private double dx;
    private double dy;

    // برای جهت‌دهی
    public Egg(double x, double y, double dx, double dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        dx *= 1.1;
        dy *= 1.05;
        x += dx;
        y += dy;
    }

    public int getX() { return (int) x; }
    public int getY() { return (int) y; }

    public void draw(Graphics g) {
        if (AssetManager.egg != null) {
            g.drawImage(AssetManager.egg, (int) x - 6, (int) y, 20, 25, null);
        } else {
            g.setColor(Color.WHITE);
            g.fillOval((int) x, (int) y, 8, 12);
            // بعضی اوقات اَسِت ها لود نمیشه برای همین من اینو اضافه کردم که اگر لود نشد یه شکل باشه که خالی نباشه !!
        }
    }
}