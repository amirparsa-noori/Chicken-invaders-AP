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
        super(x, y, 100); // جونش از 50 شد 100
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
        drawHealthBar(g, 1280); // مچ شده با رزولوشن جدید
    }

    @Override
    public void updateBoss(ArrayList<Egg> eggs) {
        x += (int) (2.5 * direction); // سرعت حرکت بیشتر شد
        if (x <= 0 || x >= 1280 - 120) direction *= -1;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= 1000) { // شلیک سریع‌تر (هر ۱ ثانیه)
            eggs.add(new Egg(x + 60, y + 60, 0, -6));
            eggs.add(new Egg(x + 60, y + 60, 0, 6));
            eggs.add(new Egg(x + 60, y + 60, -6, 0));
            eggs.add(new Egg(x + 60, y + 60, 6, 0));
            lastShotTime = currentTime;
        }
    }
}