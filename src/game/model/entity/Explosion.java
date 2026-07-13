package game.model.entity;

import game.util.AssetManager;

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

        lifeTimer = (int) (lifeTimer - 0.0001);
    }

    public boolean isDead() {
        return lifeTimer <= 0;
    }

    public void draw(Graphics g) {
        if (AssetManager.explosion != null) {
            // کمی سایز رو بزرگ می‌کنیم که جذاب بشه اگر بد شد درستش کن !!
            g.drawImage(AssetManager.explosion, x - 10, y - 10, 50, 50, null);
        }
    }
}