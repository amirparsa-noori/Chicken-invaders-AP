package game.model.enemy;

import game.util.AssetManager;

import java.awt.Color;
import java.awt.Graphics;

public class FastEnemy extends Enemy {
    public FastEnemy(int x, int y) {
        super(x, y, 1);
    }

    @Override
    public void draw(Graphics g) {
        if (AssetManager.fastChicken != null) {
            g.drawImage(AssetManager.fastChicken, x, y, 45, 45, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillOval(x, y, 30, 30);
            // بعضی اوقات اَسِت ها لود نمیشه برای همین من اینو اضافه کردم که اگر لود نشد یه شکل باشه که خالی نباشه !!
        }
    }
}