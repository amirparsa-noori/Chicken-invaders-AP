package game.ui;

import game.db.DatabaseManager;
import game.main.GameMain;
import game.model.entity.*;
import game.model.enemy.*;
import game.util.AssetManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    private GameMain gameMain;
    private Timer timer;
    private Plane plane;
    private boolean isPaused;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;
    private ArrayList<Egg> eggs;
    private ArrayList<Explosion> explosions;
    private int enemyDirection = 1;
    private int enemySpeed = 1;
    private int level = 1;
    private int score = 0;
    private long lastShotTime = 0;

    public GamePanel(GameMain gameMain) {
        this.gameMain = gameMain;
        setBackground(Color.BLACK);
        setFocusable(true);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        eggs = new ArrayList<>();
        explosions = new ArrayList<>();
        isPaused = false;

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (plane == null) return;
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) plane.setMoveLeft(true);
                if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) plane.setMoveRight(true);
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) plane.setMoveUp(true);
                if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) plane.setMoveDown(true);
                if (key == KeyEvent.VK_P) isPaused = !isPaused;
                if (key == KeyEvent.VK_ESCAPE) {
                    timer.stop();
                    gameMain.showPanel("MainMenu");
                }
                if (key == KeyEvent.VK_SPACE && !isPaused) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastShotTime >= plane.getFireRate()) {
                        bullets.add(new Bullet(plane.getX() + 25, plane.getY()));
                        lastShotTime = currentTime;
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (plane == null) return;
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) plane.setMoveLeft(false);
                if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) plane.setMoveRight(false);
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) plane.setMoveUp(false);
                if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) plane.setMoveDown(false);
            }
        });
        timer = new Timer(16, this);
    }

    public void startGame() {
        int activeType = 1;
        if (gameMain.getCurrentUser() != null) {
            activeType = gameMain.getCurrentUser().getActivePlane();
        }
        plane = new Plane(375, 500, activeType);
        enemies.clear();
        bullets.clear();
        eggs.clear();
        explosions.clear();
        score = 0;
        enemyDirection = 1;

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 8; col++) {
                int startX = 100 + col * 50;
                int startY = 50 + row * 40;
                if (row == 0) enemies.add(new ShooterEnemy(startX, startY));
                else if (row == 1) enemies.add(new ZigzagEnemy(startX, startY));
                else if (row == 2) enemies.add(new FastEnemy(startX, startY));
                else enemies.add(new NormalEnemy(startX, startY));
            }
        }
        isPaused = false;
        timer.start();
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (AssetManager.background != null) {
            g.drawImage(AssetManager.background, 0, 0, 800, 600, null);
        }
        if (plane != null) plane.draw(g);
        for (Enemy enemy : enemies) enemy.draw(g);
        for (Bullet bullet : bullets) bullet.draw(g);
        for (Egg egg : eggs) egg.draw(g);
        for (Explosion exp : explosions) exp.draw(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Score: " + score, 10, 20);
        if (plane != null) g.drawString("Lives: " + plane.getLives(), 10, 40);
        g.drawString("Level: " + level, 10, 60);
        if (gameMain.getCurrentUser() != null) {
            g.drawString("Player: " + gameMain.getCurrentUser().getUsername(), 10, 80);
        }

        if (isPaused) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("PAUSED", 340, 300);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isPaused) {
            if (plane != null) plane.update();
            updateEntities();
            checkCollisions();
        }
        repaint();
    }

    private void updateEntities() {
        boolean hitEdge = false;
        for (Enemy enemy : enemies) {
            enemy.setX(enemy.getX() + (enemySpeed * enemyDirection));
            if (enemy.getX() <= 0 || enemy.getX() >= 800 - 40) {
                hitEdge = true;
            }
            if (Math.random() < 0.002) {
                eggs.add(new Egg(enemy.getX() + 20, enemy.getY() + 30));
            }
        }
        if (hitEdge) {
            enemyDirection *= -1;
            for (Enemy enemy : enemies) {
                enemy.setY(enemy.getY() + 20);
                enemy.setX(enemy.getX() + (enemySpeed * enemyDirection));
            }
        }

        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet b = bullets.get(i);
            b.update();
            if (b.getY() < 0) bullets.remove(i);
        }

        for (int i = eggs.size() - 1; i >= 0; i--) {
            Egg egg = eggs.get(i);
            egg.update();
            if (egg.getY() > 600) eggs.remove(i);
        }

        for (int i = explosions.size() - 1; i >= 0; i--) {
            Explosion exp = explosions.get(i);
            exp.update();
            if (exp.isDead()) explosions.remove(i);
        }
    }

    private void checkCollisions() {
        if (plane == null) return;
        Rectangle planeRect = new Rectangle(plane.getX(), plane.getY(), 50, 50);

        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet b = bullets.get(i);
            Rectangle bRect = new Rectangle(b.getX(), b.getY(), 10, 25);
            boolean hit = false;
            for (int j = enemies.size() - 1; j >= 0; j--) {
                Enemy enemy = enemies.get(j);
                Rectangle eRect = new Rectangle(enemy.getX(), enemy.getY(), 40, 40);
                if (bRect.intersects(eRect)) {
                    enemy.takeDamage(1);
                    if (enemy.getHp() <= 0) {
                        explosions.add(new Explosion(enemy.getX(), enemy.getY()));
                        enemies.remove(j);
                        score += 10;
                    }
                    hit = true;
                    break;
                }
            }
            if (hit) bullets.remove(i);
        }

        for (int i = eggs.size() - 1; i >= 0; i--) {
            Egg egg = eggs.get(i);
            Rectangle eggRect = new Rectangle(egg.getX(), egg.getY(), 20, 25);
            if (eggRect.intersects(planeRect)) {
                explosions.add(new Explosion(plane.getX(), plane.getY()));
                eggs.remove(i);
                plane.setLives(plane.getLives() - 1);
                if (plane.getLives() <= 0) {
                    timer.stop();
                    handleGameOver();
                }
            }
        }
    }

    private void handleGameOver() {
        if (gameMain.getCurrentUser() != null) {
            int oldHigh = gameMain.getCurrentUser().getHighestScore();
            if (score > oldHigh) {
                gameMain.getCurrentUser().setHighestScore(score);
                DatabaseManager.updateScore(gameMain.getCurrentUser().getUsername(), score);
            }
        }
        JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
        gameMain.showPanel("MainMenu");
    }
}