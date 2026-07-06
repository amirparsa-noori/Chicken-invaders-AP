package game.ui;

import game.main.GameMain;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HowToPlayPanel extends JPanel {
    public HowToPlayPanel(GameMain gameMain) {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("HOW TO PLAY", SwingConstants.CENTER);
        title.setForeground(Color.YELLOW);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JTextArea infoText = new JTextArea();
        infoText.setBackground(Color.BLACK);
        infoText.setForeground(Color.WHITE);
        infoText.setFont(new Font("Arial", Font.PLAIN, 16));
        infoText.setEditable(false);
        infoText.setMargin(new Insets(20, 50, 20, 50));

        String instructions = "Game Controls:\n"
                + "- Move: W, A, S, D or Arrow Keys\n"
                + "- Shoot: Spacebar\n"
                + "- Pause/Resume: P\n"
                + "- Exit Game: Esc\n\n"
                + "Gameplay:\n"
                + "- Destroy waves of different chickens (Normal, Fast, Zigzag, Shooter) to advance levels.\n"
                + "- Boss fights happen at Level 4 and Level 8.\n"
                + "- Dodge the eggs dropped by chickens to survive.\n\n"
                + "Power-Ups (20% drop rate from normal chickens):\n"
                + "- Add Fire: Increases your simultaneous bullets permanently (Max 5).\n"
                + "- Rapid Fire: Doubles your shooting speed for 8 seconds.\n"
                + "- Extra Life: Grants you an extra heart (Max 5).\n"
                + "- Shield: Makes you invincible to eggs for 10 seconds.\n"
                + "- Freeze Bomb: Stops all enemies and eggs for 3 seconds.\n\n"
                + "Store:\n"
                + "Use your Highest Score to buy new planes with different Speeds and Fire Rates!";

        infoText.setText(instructions);
        add(infoText, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back to Menu");
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.addActionListener(e -> gameMain.showPanel("MainMenu"));
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}