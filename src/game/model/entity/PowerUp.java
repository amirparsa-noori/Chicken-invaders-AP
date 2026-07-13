package game.model.entity;

import game.util.AssetManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Image;

public class PowerUp {
    private int x;
    private int y;
    private int type;
    // 1: Add Fire, 2: Rapid Fire, 3: Extra Life, 4: Shield, 5: Freeze

    public PowerUp(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void update() {
        // پاورآپ ها کج میرن و از صفحه خارج میشن
        y += 2;
        x += (Math.random() > 0.5) ? 5 : -5;
    }

    public int getY() { return y; }
    public int getType() { return type; }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 35, 35);
    }

    public void draw(Graphics g) {
        Image img = null;
        switch (type) {
            case 1: img = AssetManager.powerAddFire; break;
            case 2: img = AssetManager.powerRapidFire; break;
            case 3: img = AssetManager.powerExtraLife; break;
            case 4: img = AssetManager.powerShield; break;
            case 5: img = AssetManager.powerFreeze; break;
        }
        if (img != null) {
            g.drawImage(img, x, y, 35, 35, null);
        } else {
            g.setColor(Color.PINK);
            g.fillOval(x, y, 35, 35);
        }
    }
}