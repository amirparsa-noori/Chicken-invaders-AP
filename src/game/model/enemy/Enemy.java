package game.model.enemy;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Enemy {
    protected int x;
    protected int y;

    protected int gridX;
    protected int gridY;

    protected int hp; // به عنوان شمارنده استفاده می ‌شه
    protected boolean isSpawning = false;

    private double flyX, flyY; // مختصات دقیق برای انیمیشن پرواز نرم

    public Enemy(int startX, int startY, int hp) {
        this.x = startX;
        this.y = startY;
        this.gridX = startX;
        this.gridY = startY;
        this.flyX = startX;
        this.flyY = startY;
        this.hp = hp;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    // متدهایی برای حرکت شبکه بدون به هم خوردن مرغ‌های در حال پرواز ( خیلی نکته داره !! )
    public int getGridX() { return gridX; }
    public void setGridX(int gridX) { this.gridX = gridX; }
    public int getGridY() { return gridY; }
    public void setGridY(int gridY) { this.gridY = gridY; }

    public void setX(int x) { this.x = x; this.gridX = x; this.flyX = x; } // برای غول‌ها
    public void setY(int y) { this.y = y; this.gridY = y; this.flyY = y; } // برای غول‌ها

    public void takeDamage(int amount) { hp -= amount; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public boolean isSpawning() { return isSpawning; }

    public Rectangle getBounds() { return new Rectangle(x, y, 40, 40); }

    // مکانیزم اسپاون از گوشه‌ها
    public void startSpawning() {
        isSpawning = true;
        // انتخاب تصادفی بین گوشه بالا چپ و بالا راست
        flyX = (Math.random() < 0.5) ? -50 : 1330;
        flyY = -50;
        x = (int) flyX;
        y = (int) flyY;
    }

    public void updateSpawningMovement() {

        long time = System.currentTimeMillis();
        // فکر کنم درست شد ؟
        if (x > 2000) x = 0;
        if (y < -500) y = 100;

        if (isSpawning) {
            x += (int) (Math.sin(time / 100.0) * 10);
            y += 5;
            if (y > gridY) {
                isSpawning = false;
                y = gridY;
            }
        } else {

            x = gridX + (int) (Math.cos(time / 50.0) * 3);
            y = gridY;
        }
    }

    public abstract int getScoreValue();
    public abstract void draw(Graphics g);
}

// موارد کامنت دار چک شود ! ممکنه باگ داشته باشه