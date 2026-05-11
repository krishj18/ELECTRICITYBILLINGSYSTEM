package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Loginframe extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public Loginframe() {
        setTitle("Electricity Billing System");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setUndecorated(false);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // Main panel - dark background
        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(new Color(15, 23, 42));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Logo/icon label
        JLabel logoLabel = new JLabel("⚡", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        main.add(logoLabel, gbc);

        // Title
        JLabel titleLabel = new JLabel("ELECTRICITY BILLING", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(56, 189, 248));
        gbc.gridy = 1;
        main.add(titleLabel, gbc);

        // Subtitle
        JLabel subLabel = new JLabel("Admin Portal", SwingConstants.CENTER);
        subLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subLabel.setForeground(new Color(100, 116, 139));
        gbc.gridy = 2;
        main.add(subLabel, gbc);

        // Divider
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(30, 41, 59));
        sep.setBackground(new Color(30, 41, 59));
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 30, 15, 30);
        main.add(sep, gbc);

        // Username label
        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.insets = new Insets(5, 50, 2, 50);
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("Arial", Font.BOLD, 11));
        userLabel.setForeground(new Color(100, 116, 139));
        main.add(userLabel, gbc);

        // Username field
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(300, 42));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBackground(new Color(30, 41, 59));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(51, 65, 85), 1),
                new EmptyBorder(5, 12, 5, 12)
        ));
        gbc.gridy = 5;
        gbc.insets = new Insets(2, 50, 10, 50);
        main.add(usernameField, gbc);

        // Password label
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 50, 2, 50);
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("Arial", Font.BOLD, 11));
        passLabel.setForeground(new Color(100, 116, 139));
        main.add(passLabel, gbc);

        // Password field
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 42));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(new Color(30, 41, 59));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(51, 65, 85), 1),
                new EmptyBorder(5, 12, 5, 12)
        ));
        gbc.gridy = 7;
        gbc.insets = new Insets(2, 50, 20, 50);
        main.add(passwordField, gbc);

        // Login button
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setPreferredSize(new Dimension(300, 44));
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setBackground(new Color(56, 189, 248));
        loginBtn.setForeground(new Color(15, 23, 42));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.addActionListener(e -> handleLogin());
        passwordField.addActionListener(e -> handleLogin());
        gbc.gridy = 8;
        gbc.insets = new Insets(5, 50, 10, 50);
        main.add(loginBtn, gbc);


        add(main);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        if (username.equals("admin") && password.equals("admin123")) {
            new DashboardFrame();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid credentials!", "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Loginframe::new);
    }
}