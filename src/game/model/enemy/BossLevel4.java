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
        drawHealthBar(g, 1280);
    }

    @Override
    public void updateBoss(ArrayList<Egg> eggs) {

        if (System.currentTimeMillis() % 100 < 10) {
            this.x = (int) (Math.random() * 1000);
            this.y = (int) (Math.random() * 300);
        }
        // آبدیت شد بررسی بشه !
        eggs.add(new Egg(x, y, Math.random() * 10, Math.random() * 10));
    }
}