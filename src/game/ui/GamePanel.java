package game.ui;

import game.main.GameMain;
import game.model.Enemy;
import game.model.NormalEnemy;
import game.model.Plane;
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
    private int enemyDirection = 1;
    private int enemySpeed = 1;
    private int level = 1;

    public GamePanel(GameMain gameMain) {
        this.gameMain = gameMain;
        setBackground(Color.BLACK);
        setFocusable(true);
        plane = new Plane(380, 500);
        enemies = new ArrayList<>();
        isPaused = false;
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
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
            }
            @Override
            public void keyReleased(KeyEvent e) {
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
        plane = new Plane(380, 500);
        enemies.clear();
        enemyDirection = 1;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 8; col++) {
                enemies.add(new NormalEnemy(100 + col * 50, 50 + row * 40));
            }
        }
        isPaused = false;
        timer.start();
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        plane.draw(g);
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Lives: " + plane.getLives(), 10, 20);
        g.drawString("Level: " + level, 10, 40);
        if (gameMain.getCurrentUser() != null) {
            g.drawString("Player: " + gameMain.getCurrentUser().getUsername(), 10, 60);
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
            plane.update();
            updateEnemies();
        }
        repaint();
    }

    private void updateEnemies() {
        boolean hitEdge = false;
        for (Enemy enemy : enemies) {
            enemy.setX(enemy.getX() + (enemySpeed * enemyDirection));
            if (enemy.getX() <= 0 || enemy.getX() >= 800 - 30) {
                hitEdge = true;
            }
        }
        if (hitEdge) {
            enemyDirection *= -1;
            for (Enemy enemy : enemies) {
                enemy.setY(enemy.getY() + 20);
                enemy.setX(enemy.getX() + (enemySpeed * enemyDirection));
            }
        }
    }
}