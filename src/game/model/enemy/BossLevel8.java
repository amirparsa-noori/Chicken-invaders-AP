package game.model.enemy;

import game.model.entity.Egg;
import game.util.AssetManager;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class BossLevel8 extends Boss {
    private double dx = 2.0;
    private double dy = 1.0;
    private long lastShotTime = 0;

    public BossLevel8(int x, int y) {
        super(x, y, 200); // جون غول آخر هم شد 200
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 160, 160);
    }

    @Override
    public void draw(Graphics g) {
        if (AssetManager.boss2 != null) g.drawImage(AssetManager.boss2, x, y, 160, 160, null);
        drawHealthBar(g, 1280);
    }

    @Override
    public void updateBoss(ArrayList<Egg> eggs) {
        x += dx * 3;
        y += dy * 3;
        if (x <= 0) dx = Math.abs(dx);
        if (x >= 1000) dx = -Math.abs(dx);
        // ببین اینو دیباگ کن !

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= 1000) {
            double[] angles = {0, 45, 90, 135, 180, 225, 270, 315};
            for (double angle : angles) {
                double rad = Math.toRadians(angle);
                eggs.add(new Egg(x + 80, y + 80, 6 * Math.cos(rad), 6 * Math.sin(rad))); // تخم‌مرغ سریع‌تر
            }
            lastShotTime = currentTime;
        }
    }
}