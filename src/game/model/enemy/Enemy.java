package game.model.enemy;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Enemy {
    protected int x, y, hp;

    public Enemy(int x, int y, int hp) {
        this.x = x; this.y = y; this.hp = hp;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void takeDamage(int amount) { hp -= amount; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public Rectangle getBounds() { return new Rectangle(x, y, 40, 40); }


    public abstract int getScoreValue();
    public abstract void draw(Graphics g);
}