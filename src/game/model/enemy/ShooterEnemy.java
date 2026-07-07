package game.model.enemy;

import game.util.AssetManager;

import java.awt.Color;
import java.awt.Graphics;

public class ShooterEnemy extends Enemy {
    public ShooterEnemy(int x, int y) {
        super(x, y, 2);
    }

    @Override
    public int getScoreValue() {
        return 25;}

    @Override
    public void draw(Graphics g) {
        if (AssetManager.shooterChicken != null) {
            g.drawImage(AssetManager.shooterChicken, x, y, 45, 45, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillOval(x, y, 30, 30);
            // بعضی اوقات اَسِت ها لود نمیشه برای همین من اینو اضافه کردم که اگر لود نشد یه شکل باشه که خالی نباشه !!
        }
    }
}