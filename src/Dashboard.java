import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame {
    
    private boolean isDarkMode = false;
    private ExpenseManager manager;
    private DefaultTableModel tableModel;

    public Dashboard() {
        setTitle("Expense Tracker with Analytics");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        manager = new ExpenseManager();

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Auto-fill today's date
        JTextField dateField = new JTextField(java.time.LocalDate.now().toString());
        
        // Use a dropdown for predefined categories
        String[] categories = {"Food", "Transport", "Utilities", "Shopping", "Entertainment", "Health", "Other"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        
        JTextField amountField = new JTextField();
        JTextField descField = new JTextField();

        formPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryCombo);
        formPanel.add(new JLabel("Amount:"));
        formPanel.add(amountField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descField);

        JButton addBtn = new JButton("Add Expense");

        // Group the form and the add button neatly
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        topContainer.add(buttonPanel, BorderLayout.SOUTH);

        add(topContainer, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"Date", "Category", "Amount", "Description"}, 0);

        JTable table = new JTable(tableModel);
        refreshTable();

        // Add sorting and filtering
        javax.swing.table.TableRowSorter<DefaultTableModel> sorter = new javax.swing.table.TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton deleteBtn = new JButton("Delete Selected");
        JButton chartBtn = new JButton("Show Charts");

        // Add a Search Box
        JTextField searchField = new JTextField(15);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            private void filter() {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        // Custom Pill-Shaped Dark Mode Toggle
        ThemeToggle themeToggle = new ThemeToggle((isDark) -> {
            this.isDarkMode = isDark;
            applyTheme(this, isDark);
            SwingUtilities.updateComponentTreeUI(this);
            applyTheme(this, isDark);
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        bottom.add(new JLabel("Search:"));
        bottom.add(searchField);
        bottom.add(deleteBtn);
        bottom.add(chartBtn);
        bottom.add(Box.createHorizontalStrut(20));
        bottom.add(themeToggle);

        add(bottom, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            try {
                String date = dateField.getText().trim();
                String category = (String) categoryCombo.getSelectedItem();
                double amt = Double.parseDouble(amountField.getText().trim());
                String desc = descField.getText().trim();

                if (date.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in the date.");
                    return;
                }

                manager.addExpense(new Expense(date, category, amt, desc));
                refreshTable();
                
                // Clear inputs for the next entry
                amountField.setText("");
                descField.setText("");
                dateField.setText(java.time.LocalDate.now().toString());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for amount.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding expense.");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int modelRow = table.convertRowIndexToModel(row);
                manager.deleteExpense(modelRow);
                refreshTable();
            }
        });

        chartBtn.addActionListener(e -> {
            JFrame f = new JFrame("Analytics Charts");
            f.setSize(900, 700);
            f.setLocationRelativeTo(this);
            
            JTabbedPane tabbedPane = new JTabbedPane();
            
            JPanel piePanel = new JPanel(new BorderLayout());
            piePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            piePanel.add(Analytics.createPieChart(manager.getCategoryTotals(), isDarkMode), BorderLayout.CENTER);
            
            JPanel barPanel = new JPanel(new BorderLayout());
            barPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            barPanel.add(Analytics.createBarChart(manager.getCategoryTotals(), isDarkMode), BorderLayout.CENTER);

            tabbedPane.addTab("Pie Chart (Distribution)", piePanel);
            tabbedPane.addTab("Bar Chart (Category)", barPanel);
            
            // Add Time Comparison Chart
            java.time.LocalDate now = java.time.LocalDate.now();
            java.time.format.DateTimeFormatter ymFormatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM");
            java.time.format.DateTimeFormatter yFormatter = java.time.format.DateTimeFormatter.ofPattern("yyyy");
            
            String currentMonth = now.format(ymFormatter);
            String prevMonth = now.minusMonths(1).format(ymFormatter);
            String currentYear = now.format(yFormatter);
            String prevYear = now.minusYears(1).format(yFormatter);
            
            double curMonthTotal = manager.getTotalForPrefix(currentMonth);
            double prevMonthTotal = manager.getTotalForPrefix(prevMonth);
            double curYearTotal = manager.getTotalForPrefix(currentYear);
            double prevYearTotal = manager.getTotalForPrefix(prevYear);

            JPanel compPanel = new JPanel(new BorderLayout());
            compPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            compPanel.add(Analytics.createComparisonChart(curMonthTotal, prevMonthTotal, curYearTotal, prevYearTotal, isDarkMode), BorderLayout.CENTER);
            
            tabbedPane.addTab("Time Comparison", compPanel);

            tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));

            f.add(tabbedPane);
            applyTheme(f, isDarkMode);
            SwingUtilities.updateComponentTreeUI(f);
            applyTheme(f, isDarkMode);
            f.setVisible(true);
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("en", "IN"));

        for (Expense e : manager.getAllExpenses()) {
            tableModel.addRow(new Object[]{
                    e.getDate(), e.getCategory(), format.format(e.getAmount()), e.getDescription()
            });
        }
    }
    
    private void applyTheme(Component c, boolean isDark) {
        Color defaultBg = new Color(238, 238, 238);
        Color defaultFg = Color.BLACK;
        Color defaultFieldBg = Color.WHITE;
        Color defaultTableBg = Color.WHITE;
        
        Color bg = isDark ? new Color(45, 45, 45) : defaultBg;
        Color fg = isDark ? Color.WHITE : defaultFg;
        Color fieldBg = isDark ? new Color(70, 70, 70) : defaultFieldBg;
        Color fieldFg = isDark ? Color.WHITE : defaultFg;
        Color tableBg = isDark ? new Color(55, 55, 55) : defaultTableBg;
        
        if (c instanceof JFrame || c instanceof JPanel || c instanceof JScrollPane) {
            c.setBackground(bg);
            c.setForeground(fg);
        } else if (c instanceof JLabel || c instanceof JCheckBox) {
            c.setForeground(fg);
        } else if (c instanceof JTextField || c instanceof JComboBox) {
            c.setBackground(fieldBg);
            c.setForeground(fieldFg);
            if (c instanceof JTextField) {
                ((JTextField)c).setCaretColor(isDark ? Color.WHITE : Color.BLACK);
            }
        } else if (c instanceof JButton || c instanceof JToggleButton) {
            c.setBackground(isDark ? new Color(80, 80, 80) : UIManager.getColor("Button.background"));
            c.setForeground(fg);
        } else if (c instanceof JTable) {
            c.setBackground(tableBg);
            c.setForeground(fg);
            ((JTable)c).getTableHeader().setBackground(isDark ? new Color(40, 40, 40) : UIManager.getColor("TableHeader.background"));
            ((JTable)c).getTableHeader().setForeground(fg);
        }

        if (c instanceof JScrollPane) {
            ((JScrollPane)c).getViewport().setBackground(bg);
        }

        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                applyTheme(child, isDark);
            }
        }
    }
}

