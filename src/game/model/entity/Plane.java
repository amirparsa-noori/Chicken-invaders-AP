package game.model.entity;

import game.util.AssetManager;
import java.awt.Color;
import java.awt.Graphics;

public class Plane {
    private int x, y;
    private int speed;
    private int lives;
    private int type;
    private int fireRate;
    private boolean moveLeft, moveRight, moveUp, moveDown;

    // متغیرهای جدید برای پاورآپ‌ها و سرعت دینامیک ( این باید یه چک بشه فکر کنم باید اصلاح بشه )
    private double speedMultiplier = 1.0;
    private int bulletCount = 1;
    private long shieldEndTime = 0;
    private long rapidFireEndTime = 0;

    public Plane(int startX, int startY, int type) {
        this.x = startX; this.y = startY; this.type = type;
        if (type == 2) { this.speed = 7; this.lives = 3; this.fireRate = 250; }
        else if (type == 3) { this.speed = 4; this.lives = 5; this.fireRate = 200; }
        else if (type == 4) { this.speed = 5; this.lives = 3; this.fireRate = 150; }
        else { this.speed = 5; this.lives = 3; this.fireRate = 300; }
    }

    public void setMoveLeft(boolean m) { this.moveLeft = m; }
    public void setMoveRight(boolean m) { this.moveRight = m; }
    public void setMoveUp(boolean m) { this.moveUp = m; }
    public void setMoveDown(boolean m) { this.moveDown = m; }

    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getType() { return type; }

    public void setSpeedMultiplier(double mult) { this.speedMultiplier = mult; }
    public void addBullet() { if (bulletCount < 5) bulletCount++; }
    public int getBulletCount() { return bulletCount; }
    public void activateShield() { shieldEndTime = System.currentTimeMillis() + 10000; }
    public void activateRapidFire() { rapidFireEndTime = System.currentTimeMillis() + 8000; }

    public boolean hasShield() { return System.currentTimeMillis() < shieldEndTime; }
    public boolean hasRapidFire() { return System.currentTimeMillis() < rapidFireEndTime; }
    public int getFireRate() { return hasRapidFire() ? fireRate / 2 : fireRate; }

    public void update() {
        int currentSpeed = (int) (speed * speedMultiplier);
        if (moveLeft && x > 0) x -= currentSpeed;
        if (moveRight && x < 1280 - 60) x += currentSpeed; // لبه راست
        if (moveUp && y > 350) y -= currentSpeed;
        if (moveDown && y < 720 - 80) y += currentSpeed;   // لبه پایین
    }

    public void draw(Graphics g) {
        int index = type - 1;
        if (index >= 0 && index < 7 && AssetManager.planes[index] != null) {
            g.drawImage(AssetManager.planes[index], x, y, 50, 50, null);
        }

        if (hasShield()) {
            g.setColor(new Color(0, 150, 255, 100)); // آبی نیمه‌شفاف ( اگر بد بود عوضش کن )
            g.fillOval(x - 10, y - 10, 70, 70);
            g.setColor(Color.CYAN);
            g.drawOval(x - 10, y - 10, 70, 70);
        }
    }
}