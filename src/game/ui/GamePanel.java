package game.ui;

import game.db.DatabaseManager;
import game.main.GameMain;
import game.model.entity.*;
import game.model.enemy.*;
import game.util.AssetManager;
import game.util.SoundManager;
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
    private boolean isGameOverState; // متغیر جدید برای قفل کردن کیبورد آخر بازی فکر کنم ایرادش اینطوری اوکی شه

    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;
    private ArrayList<Egg> eggs;
    private ArrayList<Explosion> explosions;
    private ArrayList<PowerUp> powerUps;

    private int level = 1;
    private int score = 0;
    private long lastShotTime = 0;
    private long freezeEndTime = 0;

    private double enemySpeed = 1;
    private int enemyDirection = 1;
    private int enemyDownStep = 20;
    private double eggProbability = 0.002;

    public GamePanel(GameMain gameMain) {
        this.gameMain = gameMain;
        setBackground(Color.BLACK);
        setFocusable(true);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        eggs = new ArrayList<>();
        explosions = new ArrayList<>();
        powerUps = new ArrayList<>();
        isPaused = false;
        isGameOverState = false;

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (plane == null || isGameOverState) return; // قفل شدن دکمه‌ها اگه بازی تموم شده باشه که نپره بیرون یهو

                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) plane.setMoveLeft(true);
                if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) plane.setMoveRight(true);
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) plane.setMoveUp(true);
                if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) plane.setMoveDown(true);

                if (key == KeyEvent.VK_P) isPaused = !isPaused;

                // تاییدیه خروج با دکمه Esc --> اینجوری بهتره خیلی
                if (key == KeyEvent.VK_ESCAPE) {
                    boolean wasPaused = isPaused;
                    isPaused = true;
                    int ans = JOptionPane.showConfirmDialog(GamePanel.this,
                            "Are you sure you want to exit to menu?", "Exit Warning", JOptionPane.YES_NO_OPTION);
                    if (ans == JOptionPane.YES_OPTION) {
                        timer.stop();
                        gameMain.showPanel("MainMenu");
                    } else {
                        isPaused = wasPaused;
                    }
                }

                if (key == KeyEvent.VK_SPACE && !isPaused) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastShotTime >= plane.getFireRate()) {
                        int bc = plane.getBulletCount();
                        int px = plane.getX();
                        int py = plane.getY();

                        if(bc == 1) bullets.add(new Bullet(px + 25, py));
                        else if(bc == 2) { bullets.add(new Bullet(px + 15, py)); bullets.add(new Bullet(px + 35, py)); }
                        else if(bc == 3) { bullets.add(new Bullet(px + 5, py)); bullets.add(new Bullet(px + 25, py)); bullets.add(new Bullet(px + 45, py)); }
                        else if(bc == 4) { bullets.add(new Bullet(px, py)); bullets.add(new Bullet(px + 15, py)); bullets.add(new Bullet(px + 35, py)); bullets.add(new Bullet(px + 50, py)); }
                        else { bullets.add(new Bullet(px, py)); bullets.add(new Bullet(px + 12, py)); bullets.add(new Bullet(px + 25, py)); bullets.add(new Bullet(px + 38, py)); bullets.add(new Bullet(px + 50, py)); }

                        lastShotTime = currentTime;
                        SoundManager.playSound("assets/sound-effects/mixkit-short-laser-gun-shot-1670.wav", "shoot");
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (plane == null || isGameOverState) return;
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
        plane = new Plane(600, 600, activeType);
        score = 0;
        level = 1;
        freezeEndTime = 0;
        isGameOverState = false;
        loadLevel(level);
        isPaused = false;
        timer.start();
        requestFocusInWindow();
    }

    private void loadLevel(int lvl) {
        enemies.clear(); bullets.clear(); eggs.clear(); explosions.clear(); powerUps.clear();
        enemyDirection = 1;

        if (plane != null) plane.setSpeedMultiplier(1.0 + ((lvl - 1) * 0.15));

        if (lvl == 4) { enemies.add(new BossLevel4(580, 50)); return; }
        else if (lvl == 8) { enemies.add(new BossLevel8(560, 50)); return; }

        int hpBase = (lvl >= 5) ? 3 : 2;

        // بازگشت به تنظیمات سرعت و احتمال تخم‌مرغ قبلی
        if (lvl == 1) { enemySpeed = 1.0; enemyDownStep = 15; eggProbability = 0.0002; }
        else if (lvl == 2) { enemySpeed = 1.2; enemyDownStep = 15; eggProbability = 0.0005; }
        else if (lvl == 3) { enemySpeed = 1.5; enemyDownStep = 20; eggProbability = 0.0008; }
        else if (lvl == 5) { enemySpeed = 1.8; enemyDownStep = 20; eggProbability = 0.0010; }
        else if (lvl == 6) { enemySpeed = 2.0; enemyDownStep = 25; eggProbability = 0.0015; }
        else if (lvl == 7) { enemySpeed = 2.5; enemyDownStep = 25; eggProbability = 0.0020; }

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 8; col++) {
                int startX = 250 + col * 90;
                int startY = 50 + row * 60;
                Enemy e;
                if (lvl == 1) e = new NormalEnemy(startX, startY);
                else if (lvl == 2) e = (row % 2 == 0) ? new NormalEnemy(startX, startY) : new FastEnemy(startX, startY);
                else if (lvl == 3) e = (row % 2 == 0) ? new NormalEnemy(startX, startY) : new ZigzagEnemy(startX, startY);
                else if (lvl == 5) e = (row % 2 == 0) ? new ShooterEnemy(startX, startY) : new FastEnemy(startX, startY);
                else if (lvl == 6) e = (row % 2 == 0) ? new ZigzagEnemy(startX, startY) : new ShooterEnemy(startX, startY);
                else {
                    if (row == 0) e = new ShooterEnemy(startX, startY);
                    else if (row == 1) e = new ZigzagEnemy(startX, startY);
                    else if (row == 2) e = new FastEnemy(startX, startY);
                    else e = new NormalEnemy(startX, startY);
                }
                e.setHp(hpBase);
                enemies.add(e);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (AssetManager.background != null) g.drawImage(AssetManager.background, 0, 0, 1280, 720, null);

        boolean isFrozen = System.currentTimeMillis() < freezeEndTime;
        if (isFrozen) {
            g.setColor(new Color(0, 200, 255, 50));
            g.fillRect(0, 0, 1280, 720);
        }

        if (plane != null) plane.draw(g);
        for (Enemy enemy : enemies) enemy.draw(g);
        for (Bullet bullet : bullets) bullet.draw(g);
        for (Egg egg : eggs) egg.draw(g);
        for (Explosion exp : explosions) exp.draw(g);
        for (PowerUp pu : powerUps) pu.draw(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score, 20, 30);
        if (plane != null) g.drawString("Lives: " + plane.getLives(), 20, 60);
        g.drawString("Level: " + level, 20, 90);
        if (gameMain.getCurrentUser() != null) {
            g.drawString("Player: " + gameMain.getCurrentUser().getUsername(), 20, 120);
        }

        if (isPaused) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("PAUSED", 550, 360);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isPaused && !isGameOverState) {
            if (plane != null) plane.update();
            updateEntities();
            checkCollisions();
            checkLevelProgress();
        }
        repaint();
    }

    private void updateEntities() {
        boolean hitEdge = false;
        boolean isFrozen = System.currentTimeMillis() < freezeEndTime;

        if (!isFrozen) {
            for (Enemy enemy : enemies) {
                if (enemy instanceof Boss) {
                    ((Boss) enemy).updateBoss(eggs);
                } else {
                    enemy.setX(enemy.getX() + (int) (enemySpeed * enemyDirection));
                    if (enemy.getX() <= 0 || enemy.getX() >= 1280 - 50) hitEdge = true; // لبه 1280

                    if (Math.random() < eggProbability) {
                        eggs.add(new Egg(enemy.getX() + 20, enemy.getY() + 30, 0, 5));
                    }

                    if (enemy.getY() > 720) enemy.setY(-50);
                }
            }
            if (hitEdge) {
                enemyDirection *= -1;
                for (Enemy enemy : enemies) {
                    if (!(enemy instanceof Boss)) {
                        enemy.setY(enemy.getY() + enemyDownStep);
                        enemy.setX(enemy.getX() + (int) (enemySpeed * enemyDirection));
                    }
                }
            }
            for (int i = eggs.size() - 1; i >= 0; i--) {
                Egg egg = eggs.get(i);
                egg.update();
                if (egg.getY() > 750 || egg.getY() < 0 || egg.getX() < 0 || egg.getX() > 1280) eggs.remove(i);
            }
        }

        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet b = bullets.get(i);
            b.update();
            if (b.getY() < 0) bullets.remove(i);
        }

        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp p = powerUps.get(i);
            p.update();
            if (p.getY() > 750) powerUps.remove(i);
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
                if (bRect.intersects(enemy.getBounds())) {
                    int damage = (enemy instanceof Boss && plane.getType() == 4) ? 2 : 1;
                    enemy.takeDamage(damage);
                    if (enemy.getHp() <= 0) {
                        explosions.add(new Explosion(enemy.getX(), enemy.getY()));
                        SoundManager.playSound("assets/sound-effects/mixkit-epic-impact-afar-explosion-2782.wav", "explosion");

                        if (!(enemy instanceof Boss) && Math.random() < 0.20) {
                            int randomType = (int) (Math.random() * 5) + 1;
                            powerUps.add(new PowerUp(enemy.getX(), enemy.getY(), randomType));
                        }
                        enemies.remove(j);
                        score += (enemy instanceof Boss) ? 0 : 10;
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
                eggs.remove(i);
                if (!plane.hasShield()) {
                    explosions.add(new Explosion(plane.getX(), plane.getY()));
                    SoundManager.playSound("assets/sound-effects/mixkit-epic-impact-afar-explosion-2782.wav", "explosion");
                    plane.setLives(plane.getLives() - 1);
                    if (plane.getLives() <= 0) {
                        handleGameOver("Game Over!");
                        return;
                    }
                }
            }
        }

        for (int j = enemies.size() - 1; j >= 0; j--) {
            Enemy enemy = enemies.get(j);
            if (enemy.getBounds().intersects(planeRect)) {
                if (!plane.hasShield()) {
                    explosions.add(new Explosion(plane.getX(), plane.getY()));
                    SoundManager.playSound("assets/sound-effects/mixkit-epic-impact-afar-explosion-2782.wav", "explosion");
                    plane.setLives(plane.getLives() - 1);
                    enemies.remove(j);
                    if (plane.getLives() <= 0) {
                        handleGameOver("Game Over!");
                        return;
                    }
                } else {
                    explosions.add(new Explosion(enemy.getX(), enemy.getY()));
                    SoundManager.playSound("assets/sound-effects/mixkit-epic-impact-afar-explosion-2782.wav", "explosion");
                    enemies.remove(j);
                    score += 10;
                }
            }
        }

        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp p = powerUps.get(i);
            if (p.getBounds().intersects(planeRect)) {
                switch(p.getType()) {
                    case 1: plane.addBullet(); break;
                    case 2: plane.activateRapidFire(); break;
                    case 3: if(plane.getLives() < 5) plane.setLives(plane.getLives() + 1); break;
                    case 4: plane.activateShield(); break;
                    case 5: freezeEndTime = System.currentTimeMillis() + 3000; break;
                }
                powerUps.remove(i);
            }
        }
    }

    private void checkLevelProgress() {
        if (enemies.isEmpty()) {
            if (level == 4) score += 500;
            else if (level == 8) {
                score += 1000;
                handleGameOver("You Win! Victory!");
                return;
            }
            else { score += 200; }

            level++;
            loadLevel(level);
        }
    }

    private void handleGameOver(String msg) {
        if(isGameOverState) return; // جلوگیری از دوبار اجرا شدن / این فکر کنم بهتر بشه
        isGameOverState = true;
        timer.stop();

        if (gameMain.getCurrentUser() != null) {
            int oldHigh = gameMain.getCurrentUser().getHighestScore();
            if (score > oldHigh) {
                gameMain.getCurrentUser().setHighestScore(score);
                DatabaseManager.updateScore(gameMain.getCurrentUser().getUsername(), score);
            }
        }


        SoundManager.stopMusic();
        if (msg.contains("Win")) {

            SoundManager.playMusic("assets/sound-effects/Chicken Invaders 2 Remastered OST - Ending Theme.wav");
        } else {
            SoundManager.playSound("assets/sound-effects/mixkit-retro-arcade-game-over-470.wav", "gameover");
        }


        Timer delayTimer = new Timer(1500, evt -> {
            JOptionPane.showMessageDialog(this, msg + "\nFinal Score: " + score);
            gameMain.showPanel("MainMenu");
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }
}