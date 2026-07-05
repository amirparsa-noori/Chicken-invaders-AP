package game.model.enemy;

import java.awt.Graphics;

public abstract class Enemy {
    protected int x;
    protected int y;
    protected int hp;

    public Enemy(int x, int y, int hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void takeDamage(int amount) { hp -= amount; }
    public int getHp() { return hp; }

    public abstract void draw(Graphics g);
}