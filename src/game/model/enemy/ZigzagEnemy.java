package game.model.enemy;

import game.util.AssetManager;

import java.awt.Color;
import java.awt.Graphics;

public class ZigzagEnemy extends Enemy {
    public ZigzagEnemy(int x, int y) {
        super(x, y, 2);
    }

    @Override
    public void draw(Graphics g) {
        if (AssetManager.zigzagChicken != null) {
            g.drawImage(AssetManager.zigzagChicken, x, y, 45, 45, null);
        } else {
            g.setColor(Color.MAGENTA);
            g.fillOval(x, y, 30, 30);
        }
    }
}