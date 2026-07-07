package game.model.enemy;

import game.util.AssetManager;

import java.awt.Color;
import java.awt.Graphics;

public class NormalEnemy extends Enemy {

    public NormalEnemy(int x, int y) {
        super(x, y, 2);
    }
    @Override
    public int getScoreValue() { return 10; }

    @Override
    public void draw(Graphics g) {
        if (AssetManager.normalChicken != null) {
            g.drawImage(AssetManager.normalChicken, x, y, 45, 45, null);
        } else {
            g.setColor(Color.RED);
            g.fillOval(x, y, 30, 30);
            // بعضی اوقات اَسِت ها لود نمیشه برای همین من اینو اضافه کردم که اگر لود نشد یه شکل باشه که خالی نباشه !!
        }
    }
}