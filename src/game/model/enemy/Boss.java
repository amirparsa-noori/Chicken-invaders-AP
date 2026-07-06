package game.model.enemy;

import game.model.entity.Egg;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class Boss extends Enemy {
    protected int maxHp;

    public Boss(int x, int y, int maxHp) {
        super(x, y, maxHp);
        this.maxHp = maxHp;
    }

    public void drawHealthBar(Graphics g, int screenWidth) {
        g.setColor(Color.RED);
        g.fillRect(screenWidth / 2 - 150, 20, 300, 15);
        g.setColor(Color.GREEN);
        int hpWidth = (int) ((hp / (double) maxHp) * 300);
        if (hpWidth < 0) hpWidth = 0;
        g.fillRect(screenWidth / 2 - 150, 20, hpWidth, 15);
        g.setColor(Color.WHITE);
        g.drawRect(screenWidth / 2 - 150, 20, 300, 15);
    }

    public abstract void updateBoss(ArrayList<Egg> eggs);
}