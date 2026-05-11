package UI;

import DAO.customerDAO;
import MODEL.customer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddCustomerFrame extends JFrame {

    private JTextField nameField, addressField, meterField, phoneField;
    private customerDAO custDAO = new customerDAO();

    private static final Color BG_DARK     = new Color(15, 23, 42);
    private static final Color BG_CARD     = new Color(30, 41, 59);
    private static final Color ACCENT_BLUE = new Color(56, 189, 248);
    private static final Color TEXT_MUTED  = new Color(100, 116, 139);
    private static final Color BORDER      = new Color(51, 65, 85);

    public AddCustomerFrame() {
        setTitle("Manage Customers");
        setSize(550, 620);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(400, 500));
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

        JLabel navTitle = new JLabel("⚡  Add New Customer");
        navTitle.setFont(new Font("Arial", Font.BOLD, 18));
        navTitle.setForeground(ACCENT_BLUE);
        navbar.add(navTitle, BorderLayout.WEST);

        // Maximize toggle
        JButton toggleBtn = new JButton("⛶  Maximize");
        toggleBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        toggleBtn.setBackground(BG_CARD);
        toggleBtn.setForeground(Color.WHITE);
        toggleBtn.setFocusPainted(false);
        toggleBtn.setBorderPainted(false);
        toggleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleBtn.addActionListener(e -> {
            if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                setExtendedState(JFrame.NORMAL);
                toggleBtn.setText("⛶  Maximize");
            } else {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                toggleBtn.setText("❐  Restore");
            }
        });
        navbar.add(toggleBtn, BorderLayout.EAST);
        main.add(navbar, BorderLayout.NORTH);

        // ── Form card ──
        JPanel cardWrapper = new JPanel(new GridBagLayout());
        cardWrapper.setBackground(BG_DARK);
        cardWrapper.setBorder(new EmptyBorder(30, 40, 30, 40));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.gridwidth = 2;

        // Section title
        JLabel sectionTitle = new JLabel("Customer Information");
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 16));
        sectionTitle.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0;
        card.add(sectionTitle, gbc);

        // Divider
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        sep.setBackground(BORDER);
        gbc.gridy = 1;
        gbc.insets = new Insets(2, 6, 15, 6);
        card.add(sep, gbc);

        // Fields
        gbc.insets = new Insets(6, 6, 6, 6);
        nameField    = addFormField(card, gbc, "FULL NAME", 2, "Enter full name");
        addressField = addFormField(card, gbc, "ADDRESS", 3, "Enter address");
        meterField   = addFormField(card, gbc, "METER NUMBER", 4, "Enter meter number");
        phoneField   = addFormField(card, gbc, "PHONE NUMBER", 5, "Enter phone number");

        // Buttons row
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnRow.setBackground(BG_CARD);

        JButton clearBtn = new JButton("Clear");
        clearBtn.setFont(new Font("Arial", Font.BOLD, 13));
        clearBtn.setBackground(new Color(51, 65, 85));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFocusPainted(false);
        clearBtn.setBorderPainted(false);
        clearBtn.setPreferredSize(new Dimension(120, 40));
        clearBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearBtn.addActionListener(e -> clearFields());

        JButton saveBtn = new JButton("Save Customer");
        saveBtn.setFont(new Font("Arial", Font.BOLD, 13));
        saveBtn.setBackground(ACCENT_BLUE);
        saveBtn.setForeground(new Color(15, 23, 42));
        saveBtn.setFocusPainted(false);
        saveBtn.setBorderPainted(false);
        saveBtn.setPreferredSize(new Dimension(150, 40));
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.addActionListener(e -> saveCustomer());

        btnRow.add(clearBtn);
        btnRow.add(saveBtn);

        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 6, 6, 6);
        card.add(btnRow, gbc);

        GridBagConstraints wrapGbc = new GridBagConstraints();
        wrapGbc.fill = GridBagConstraints.BOTH;
        wrapGbc.weightx = 1;
        wrapGbc.weighty = 1;
        cardWrapper.add(card, wrapGbc);

        main.add(cardWrapper, BorderLayout.CENTER);
        add(main);
    }

    // Helper - creates a label + field pair
    private JTextField addFormField(JPanel parent, GridBagConstraints gbc,
                                    String label, int row, String placeholder) {
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.insets = new Insets(8, 6, 2, 6);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 11));
        lbl.setForeground(TEXT_MUTED);
        parent.add(lbl, gbc);

        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(0, 40));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(new Color(15, 23, 42));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(5, 12, 5, 12)
        ));

        gbc.gridx = 1; gbc.gridy = row;
        gbc.insets = new Insets(8, 6, 2, 6);
        parent.add(field, gbc);
        return field;
    }

    private void saveCustomer() {
        if (nameField.getText().trim().isEmpty() ||
                addressField.getText().trim().isEmpty() ||
                meterField.getText().trim().isEmpty() ||
                phoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "All fields are required!", "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        customer c = new customer(0,
                nameField.getText().trim(),
                addressField.getText().trim(),
                meterField.getText().trim(),
                phoneField.getText().trim()
        );
        boolean success = custDAO.addcustomer(c);
        if (success) {
            JOptionPane.showMessageDialog(this, "Customer added successfully!");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed! Meter number may already exist.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        addressField.setText("");
        meterField.setText("");
        phoneField.setText("");
    }
}