package game.ui;

import game.db.DatabaseManager;
import game.main.GameMain;
import game.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private GameMain gameMain;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPanel(GameMain gameMain) {
        this.gameMain = gameMain;
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel title = new JLabel("Login / Register", SwingConstants.CENTER);
        title.setForeground(Color.YELLOW);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        add(userLabel, gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        add(passLabel, gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = usernameField.getText();
                String pass = new String(passwordField.getPassword());
                User loggedIn = DatabaseManager.loginUser(user, pass);
                if (loggedIn != null) {
                    gameMain.setCurrentUser(loggedIn);
                    gameMain.showPanel("MainMenu");
                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Invalid credentials!");
                }
            }
        });
        add(loginBtn, gbc);
        gbc.gridx = 1;
        JButton regBtn = new JButton("Register");
        regBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = usernameField.getText();
                String pass = new String(passwordField.getPassword());
                if (DatabaseManager.registerUser(user, pass)) {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Registered successfully! Now Login.");
                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Username already exists.");
                }
            }
        });
        add(regBtn, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMain.showPanel("MainMenu");
            }
        });
        add(backBtn, gbc);
    }
}