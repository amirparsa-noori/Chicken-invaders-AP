package game.model.enemy;

import game.model.entity.Egg;
import game.util.AssetManager;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class BossLevel4 extends Boss {
    private int direction = 1;
    private long lastShotTime = 0;

    public BossLevel4(int x, int y) {
        super(x, y, 50); // 50 جان ( اینو اگر ساده بود تغییر بده )
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 120, 120);
    }

    @Override
    public void draw(Graphics g) {
        if (AssetManager.boss1 != null) {
            g.drawImage(AssetManager.boss1, x, y, 120, 120, null);
        }
        drawHealthBar(g, 800);
    }

    @Override
    public void updateBoss(ArrayList<Egg> eggs) {
        x += (int) (1.5 * direction);
        if (x <= 0 || x >= 800 - 120) direction *= -1;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= 1500) {
            // شلیک 4 جهته
            eggs.add(new Egg(x + 60, y + 60, 0, -4)); // بالا
            eggs.add(new Egg(x + 60, y + 60, 0, 4));  // پایین
            eggs.add(new Egg(x + 60, y + 60, -4, 0)); // چپ
            eggs.add(new Egg(x + 60, y + 60, 4, 0));  // راست
            lastShotTime = currentTime;
        }
    }
}