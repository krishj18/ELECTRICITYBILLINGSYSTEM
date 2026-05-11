package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private static final Color BG_DARK     = new Color(15, 23, 42);
    private static final Color BG_CARD     = new Color(30, 41, 59);
    private static final Color ACCENT_BLUE = new Color(56, 189, 248);
    private static final Color TEXT_MUTED  = new Color(100, 116, 139);

    public DashboardFrame() {
        setTitle("Electricity Billing System - Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 400));
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG_DARK);

        // ── Top navbar ──
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(new Color(20, 30, 55));
        navbar.setBorder(new EmptyBorder(15, 25, 15, 25));
        navbar.setPreferredSize(new Dimension(0, 65));

        JLabel navTitle = new JLabel("⚡  ELECTRICITY BILLING SYSTEM");
        navTitle.setFont(new Font("Arial", Font.BOLD, 18));
        navTitle.setForeground(ACCENT_BLUE);
        navbar.add(navTitle, BorderLayout.WEST);

        // Window size toggle button
        JButton toggleBtn = new JButton("[]  Maximize");
        toggleBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        toggleBtn.setBackground(new Color(30, 41, 59));
        toggleBtn.setForeground(Color.WHITE);
        toggleBtn.setFocusPainted(false);
        toggleBtn.setBorderPainted(false);
        toggleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleBtn.addActionListener(e -> {
            if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                setExtendedState(JFrame.NORMAL);
                toggleBtn.setText("[]  Maximize");
            } else {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                toggleBtn.setText("[]  Restore");
            }
        });
        navbar.add(toggleBtn, BorderLayout.EAST);
        main.add(navbar, BorderLayout.NORTH);

        // ── Center content ──
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(BG_DARK);
        content.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Welcome text
        JLabel welcome = new JLabel("Welcome, Admin");
        welcome.setFont(new Font("Arial", Font.BOLD, 26));
        welcome.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Manage your electricity billing operations below");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_MUTED);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BG_DARK);
        headerPanel.add(welcome);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitle);
        headerPanel.add(Box.createVerticalStrut(30));
        content.add(headerPanel, BorderLayout.NORTH);

        // Cards grid
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        cardsPanel.setBackground(BG_DARK);

        cardsPanel.add(createCard("👤", "Manage Customers",
                "Add, edit or remove customers",
                new Color(99, 102, 241), e -> new AddCustomerFrame()));

        cardsPanel.add(createCard("📊", "Meter Readings",
                "Enter and track meter readings",
                new Color(20, 184, 166), e -> new MeterReadingFrame()));

        cardsPanel.add(createCard("🧾", "View Bills",
                "View, update and manage bills",
                new Color(249, 115, 22), e -> new ViewBillsFrame()));

        cardsPanel.add(createCard("🚪", "Logout",
                "Sign out of the admin portal",
                new Color(239, 68, 68), e -> {
                    int c = JOptionPane.showConfirmDialog(this,
                            "Are you sure you want to logout?",
                            "Logout", JOptionPane.YES_NO_OPTION);
                    if (c == JOptionPane.YES_OPTION) {
                        new Loginframe();
                        dispose();
                    }
                }));

        content.add(cardsPanel, BorderLayout.CENTER);
        main.add(content, BorderLayout.CENTER);

        // ── Bottom status bar ──
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(new Color(20, 30, 55));
        statusBar.setBorder(new EmptyBorder(8, 25, 8, 25));
        JLabel status = new JLabel("● System Online");
        status.setFont(new Font("Arial", Font.PLAIN, 12));
        status.setForeground(new Color(34, 197, 94));
        statusBar.add(status, BorderLayout.WEST);
        main.add(statusBar, BorderLayout.SOUTH);

        add(main);
    }

    private JPanel createCard(String icon, String title,
                              String desc, Color accent,
                              java.awt.event.ActionListener action) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(51, 65, 85), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Icon + title row
        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topRow.setBackground(BG_CARD);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        iconLabel.setBorder(new EmptyBorder(0, 0, 0, 12));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        topRow.add(iconLabel);
        topRow.add(titleLabel);

        // Description
        JLabel descLabel = new JLabel(desc);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setForeground(TEXT_MUTED);

        // Action button
        JButton btn = new JButton("Open →");
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(accent);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(action);

        JPanel bottomRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomRow.setBackground(BG_CARD);
        bottomRow.add(btn);

        card.add(topRow, BorderLayout.NORTH);
        card.add(descLabel, BorderLayout.CENTER);
        card.add(bottomRow, BorderLayout.SOUTH);

        return card;
    }
}