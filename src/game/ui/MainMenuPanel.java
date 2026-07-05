package game.ui;

import game.main.GameMain;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    private GameMain gameMain;

    public MainMenuPanel(GameMain gameMain) {
        this.gameMain = gameMain;
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel titleLabel = new JLabel("CHICKEN INVADERS", SwingConstants.CENTER);
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gbc.gridy = 0;
        add(titleLabel, gbc);
        String[] buttons = {"New Game", "High Scores", "Settings", "How to Play", "Exit"};
        int y = 1;
        for (int i = 0; i < buttons.length; i++) {
            JButton btn = new JButton(buttons[i]);
            btn.setFont(new Font("Arial", Font.PLAIN, 18));
            gbc.gridy = y++;
            String buttonName = buttons[i];
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleMenuAction(buttonName);
                }
            });
            add(btn, gbc);
        }
    }

    private void handleMenuAction(String action) {
        if (action.equals("Exit")) {
            System.exit(0);
        } else if (action.equals("New Game")) {
            if (gameMain.getCurrentUser() == null) {
                gameMain.showPanel("Login");
            } else {
                gameMain.showPanel("Game");
            }
        }
    }
}