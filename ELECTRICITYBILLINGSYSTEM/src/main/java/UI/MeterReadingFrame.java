package UI;

import DAO.customerDAO;
import DAO.ReadingDAO;
import DAO.billDAO;
import MODEL.customer;
import MODEL.meterreading;
import MODEL.bill;
import UTIL.BillCalculator;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class MeterReadingFrame extends JFrame {

    private JComboBox<String> customerDropdown;
    private JTextField previousField, currentField, unitsField, amountField;
    private List<customer> customerList;

    private customerDAO custDAO  = new customerDAO();
    private ReadingDAO readingDAO = new ReadingDAO();
    private billDAO bDAO          = new billDAO();

    private static final Color BG_DARK     = new Color(15, 23, 42);
    private static final Color BG_CARD     = new Color(30, 41, 59);
    private static final Color ACCENT_BLUE = new Color(56, 189, 248);
    private static final Color TEXT_MUTED  = new Color(100, 116, 139);
    private static final Color BORDER      = new Color(51, 65, 85);

    public MeterReadingFrame() {
        setTitle("Meter Reading");
        setSize(580, 650);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(450, 550));
        initComponents();
        loadCustomers();
        setVisible(true);
    }

    private void initComponents() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG_DARK);

        // ── Navbar ──
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(new Color(20, 30, 55));
        navbar.setBorder(new EmptyBorder(15, 25, 15, 25));
        navbar.setPreferredSize(new Dimension(0, 65));

        JLabel navTitle = new JLabel("⚡  Meter Reading Entry");
        navTitle.setFont(new Font("Arial", Font.BOLD, 18));
        navTitle.setForeground(ACCENT_BLUE);
        navbar.add(navTitle, BorderLayout.WEST);

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

        // ── Card ──
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

        // Title
        JLabel sectionTitle = new JLabel("Enter Meter Reading");
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 16));
        sectionTitle.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0;
        card.add(sectionTitle, gbc);

        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        gbc.gridy = 1;
        gbc.insets = new Insets(2, 6, 15, 6);
        card.add(sep, gbc);

        // Customer dropdown
        gbc.insets = new Insets(6, 6, 2, 6);
        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = 2;
        JLabel custLbl = new JLabel("SELECT CUSTOMER");
        custLbl.setFont(new Font("Arial", Font.BOLD, 11));
        custLbl.setForeground(TEXT_MUTED);
        card.add(custLbl, gbc);

        customerDropdown = new JComboBox<>();
        customerDropdown.setFont(new Font("Arial", Font.PLAIN, 13));
        customerDropdown.setBackground(new Color(15, 23, 42));
        customerDropdown.setForeground(Color.WHITE);
        customerDropdown.setPreferredSize(new Dimension(0, 40));
        customerDropdown.addActionListener(e -> loadPreviousReading());
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.insets = new Insets(6, 6, 6, 6);
        card.add(customerDropdown, gbc);

        // Previous reading (locked)
        previousField = addReadingField(card, gbc, "PREVIOUS READING", 3, false);

        // Current reading (editable)
        currentField = addReadingField(card, gbc, "CURRENT READING", 4, true);

        // Units (locked)
        unitsField = addReadingField(card, gbc, "UNITS CONSUMED", 5, false);

        // Amount (locked)
        amountField = addReadingField(card, gbc, "BILL AMOUNT (Rs.)", 6, false);

        // Buttons
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnRow.setBackground(BG_CARD);

        JButton calcBtn = new JButton("Calculate");
        calcBtn.setFont(new Font("Arial", Font.BOLD, 13));
        calcBtn.setBackground(new Color(51, 65, 85));
        calcBtn.setForeground(Color.WHITE);
        calcBtn.setFocusPainted(false);
        calcBtn.setBorderPainted(false);
        calcBtn.setPreferredSize(new Dimension(130, 40));
        calcBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        calcBtn.addActionListener(e -> calculateBill());

        JButton generateBtn = new JButton("Generate Bill");
        generateBtn.setFont(new Font("Arial", Font.BOLD, 13));
        generateBtn.setBackground(new Color(20, 184, 166));
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setFocusPainted(false);
        generateBtn.setBorderPainted(false);
        generateBtn.setPreferredSize(new Dimension(150, 40));
        generateBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        generateBtn.addActionListener(e -> generateBill());

        btnRow.add(calcBtn);
        btnRow.add(generateBtn);

        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.insets = new Insets(20, 6, 6, 6);
        card.add(btnRow, gbc);

        GridBagConstraints wrapGbc = new GridBagConstraints();
        wrapGbc.fill = GridBagConstraints.BOTH;
        wrapGbc.weightx = 1; wrapGbc.weighty = 1;
        cardWrapper.add(card, wrapGbc);
        main.add(cardWrapper, BorderLayout.CENTER);
        add(main);
    }

    private JTextField addReadingField(JPanel parent, GridBagConstraints gbc,
                                       String label, int row, boolean editable) {
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
        field.setEditable(editable);
        field.setBackground(editable ? new Color(15, 23, 42) : new Color(20, 30, 48));
        field.setForeground(editable ? Color.WHITE : new Color(148, 163, 184));
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(editable ? BORDER : new Color(30, 41, 59), 1),
                new EmptyBorder(5, 12, 5, 12)
        ));
        gbc.gridx = 1; gbc.gridy = row;
        gbc.insets = new Insets(8, 6, 2, 6);
        parent.add(field, gbc);
        return field;
    }

    private void loadCustomers() {
        customerList = custDAO.getallCustomers();
        customerDropdown.removeAllItems();
        for (customer c : customerList) {
            customerDropdown.addItem(c.getId() + " - " + c.getName());
        }
        loadPreviousReading();
    }

    private void loadPreviousReading() {
        int index = customerDropdown.getSelectedIndex();
        if (index < 0 || customerList == null || customerList.isEmpty()) return;
        int customerId = customerList.get(index).getId();
        meterreading last = readingDAO.getLatestReading(customerId);
        previousField.setText(last != null ? String.valueOf(last.getCurrentReading()) : "0");
        currentField.setText("");
        unitsField.setText("");
        amountField.setText("");
    }

    private void calculateBill() {
        try {
            int previous = Integer.parseInt(previousField.getText().trim());
            int current  = Integer.parseInt(currentField.getText().trim());
            if (current < previous) {
                JOptionPane.showMessageDialog(this,
                        "Current reading cannot be less than previous!",
                        "Invalid", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int units = current - previous;
            double amount = BillCalculator.calculate(units);
            unitsField.setText(String.valueOf(units));
            amountField.setText(String.format("%.2f", amount));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateBill() {
        if (unitsField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please calculate first!", "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int index      = customerDropdown.getSelectedIndex();
        int customerId = customerList.get(index).getId();
        int previous   = Integer.parseInt(previousField.getText());
        int current    = Integer.parseInt(currentField.getText());
        int units      = Integer.parseInt(unitsField.getText());
        double amount  = Double.parseDouble(amountField.getText());

        meterreading reading = new meterreading(
                0, customerId, "", "", previous, current, new Date()
        );
        readingDAO.addReading(reading);

        bill newBill = new bill(
                0, customerId, "", units, amount, new Date(), "Unpaid"
        );
        bDAO.addBill(newBill);

        JOptionPane.showMessageDialog(this,
                "Bill generated!\nAmount: Rs." + amount);
        loadPreviousReading();
        currentField.setText("");
        unitsField.setText("");
        amountField.setText("");
    }
}