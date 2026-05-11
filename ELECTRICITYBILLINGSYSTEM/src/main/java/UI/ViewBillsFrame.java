package UI;

import DAO.billDAO;
import MODEL.bill;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ViewBillsFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private billDAO bDAO = new billDAO();

    private static final Color BG_DARK     = new Color(15, 23, 42);
    private static final Color BG_CARD     = new Color(30, 41, 59);
    private static final Color ACCENT_BLUE = new Color(56, 189, 248);
    private static final Color TEXT_MUTED  = new Color(100, 116, 139);
    private static final Color BORDER      = new Color(51, 65, 85);

    public ViewBillsFrame() {
        setTitle("View Bills");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(600, 400));
        initComponents();
        loadBills();
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

        JLabel navTitle = new JLabel("⚡  Bills Management");
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

        // ── Table area ──
        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(BG_DARK);
        tableWrapper.setBorder(new EmptyBorder(25, 25, 10, 25));

        String[] columns = {"Bill ID", "Customer", "Units", "Amount (Rs.)", "Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setBackground(BG_CARD);
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(51, 65, 85));
        table.setSelectionForeground(Color.WHITE);
        table.setFillsViewportHeight(true);

        // Style header
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(20, 30, 55));
        header.setForeground(ACCENT_BLUE);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 40));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER));

        // Center align all columns
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columns.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // Color status column green/red
        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object val,
                                                           boolean sel, boolean foc, int row, int col) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                        t, val, sel, foc, row, col);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setBackground(sel ? new Color(51, 65, 85) : BG_CARD);
                if ("Paid".equals(val)) {
                    lbl.setForeground(new Color(34, 197, 94));
                } else {
                    lbl.setForeground(new Color(239, 68, 68));
                }
                lbl.setOpaque(true);
                return lbl;
            }
        });

        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object val,
                                                           boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setHorizontalAlignment(SwingConstants.CENTER);
                if (sel) {
                    setBackground(new Color(51, 65, 85));
                } else {
                    setBackground(row % 2 == 0 ? BG_CARD : new Color(24, 34, 52));
                }
                setForeground(Color.WHITE);
                setOpaque(true);
                return this;
            }
        });

        // Re-apply status renderer after default renderer
        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object val,
                                                           boolean sel, boolean foc, int row, int col) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                        t, val, sel, foc, row, col);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setBackground(sel ? new Color(51, 65, 85)
                        : (row % 2 == 0 ? BG_CARD : new Color(24, 34, 52)));
                lbl.setForeground("Paid".equals(val)
                        ? new Color(34, 197, 94) : new Color(239, 68, 68));
                lbl.setOpaque(true);
                return lbl;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBackground(BG_DARK);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        scroll.getViewport().setBackground(BG_CARD);
        tableWrapper.add(scroll, BorderLayout.CENTER);
        main.add(tableWrapper, BorderLayout.CENTER);

        // ── Bottom buttons ──
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        btnBar.setBackground(BG_DARK);
        btnBar.setBorder(new EmptyBorder(0, 25, 10, 25));

        JButton refreshBtn = new JButton("🔄  Refresh");
        refreshBtn.setFont(new Font("Arial", Font.BOLD, 13));
        refreshBtn.setBackground(new Color(51, 65, 85));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorderPainted(false);
        refreshBtn.setPreferredSize(new Dimension(130, 40));
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshBtn.addActionListener(e -> loadBills());

        JButton markPaidBtn = new JButton("✅  Mark Paid");
        markPaidBtn.setFont(new Font("Arial", Font.BOLD, 13));
        markPaidBtn.setBackground(new Color(34, 197, 94));
        markPaidBtn.setForeground(new Color(15, 23, 42));
        markPaidBtn.setFocusPainted(false);
        markPaidBtn.setBorderPainted(false);
        markPaidBtn.setPreferredSize(new Dimension(130, 40));
        markPaidBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        markPaidBtn.addActionListener(e -> markAsPaid());

        JButton deleteBtn = new JButton("🗑  Delete");
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 13));
        deleteBtn.setBackground(new Color(239, 68, 68));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorderPainted(false);
        deleteBtn.setPreferredSize(new Dimension(120, 40));
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(e -> deleteBill());

        btnBar.add(refreshBtn);
        btnBar.add(markPaidBtn);
        btnBar.add(deleteBtn);
        main.add(btnBar, BorderLayout.SOUTH);
        add(main);
    }

    private void loadBills() {
        tableModel.setRowCount(0);
        List<bill> bills = bDAO.getAllBills();
        for (bill b : bills) {
            tableModel.addRow(new Object[]{
                    b.getId(),
                    b.getcustomername(),
                    b.getunitsconsumed(),
                    String.format("%.2f", b.getamount()),
                    b.getbilldate(),
                    b.getStatus()
            });
        }
    }

    private void markAsPaid() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a bill first!");
            return;
        }
        if ("Paid".equals(tableModel.getValueAt(row, 5))) {
            JOptionPane.showMessageDialog(this, "Already paid!");
            return;
        }
        int billId = (int) tableModel.getValueAt(row, 0);
        if (bDAO.updateBillStatus(billId, "Paid")) {
            tableModel.setValueAt("Paid", row, 5);
            JOptionPane.showMessageDialog(this, "Bill marked as paid!");
        }
    }

    private void deleteBill() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a bill to delete!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete this bill?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int billId = (int) tableModel.getValueAt(row, 0);
            if (bDAO.deleteBill(billId)) {
                tableModel.removeRow(row);
                JOptionPane.showMessageDialog(this, "Bill deleted!");
            }
        }
    }
}