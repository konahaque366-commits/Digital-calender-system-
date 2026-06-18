
package mycalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyCalendar extends JFrame {

    private JLabel monthLabel;
    private JButton prevButton, nextButton;
    private JTable calendarTable;
    private JScrollPane tableScrollPane;
    private int currentYear, currentMonth;

    public MyCalendar() {
        setTitle("Digital Calendar System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Digital Calendar System - Week 2"));
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        // Navigation Top Panel
        JPanel navPanel = new JPanel(new BorderLayout());
        navPanel.setBackground(new Color(230, 240, 250));
        
        monthLabel = new JLabel("", JLabel.CENTER);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        prevButton = new JButton("<< Prev");
        nextButton = new JButton("Next >>");
        
        navPanel.add(prevButton, BorderLayout.WEST);
        navPanel.add(monthLabel, BorderLayout.CENTER);
        navPanel.add(nextButton, BorderLayout.EAST);
        add(navPanel, BorderLayout.NORTH);

        // Calendar Grid Layout
        String[] columns = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        String[][] data = new String[6][7]; 
        
        calendarTable = new JTable(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tableScrollPane = new JScrollPane(calendarTable);
        calendarTable.setRowHeight(40);
        calendarTable.getTableHeader().setReorderingAllowed(false); 
        calendarTable.setCellSelectionEnabled(true);
        add(tableScrollPane, BorderLayout.CENTER);

        // Get current real-time date
        Calendar cal = new GregorianCalendar();
        currentMonth = cal.get(Calendar.MONTH);
        currentYear = cal.get(Calendar.YEAR);
        updateCalendar(currentMonth, currentYear);

        // Action Listeners
        prevButton.addActionListener(e -> {
            if (currentMonth == 0) {
                currentMonth = 11;
                currentYear--;
            } else {
                currentMonth--;
            }
            updateCalendar(currentMonth, currentYear);
        });

        nextButton.addActionListener(e -> {
            if (currentMonth == 11) {
                currentMonth = 0;
                currentYear++;
            } else {
                currentMonth++;
            }
            updateCalendar(currentMonth, currentYear);
        });
    }

    private void updateCalendar(int month, int year) {
        String[] months = {
            "January", "February", "March", "April", "May", "June", 
            "July", "August", "September", "October", "November", "December"
        };
        monthLabel.setText(months[month] + " " + year);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                calendarTable.setValueAt("", i, j);
            }
        }

        Calendar cal = new GregorianCalendar(year, month, 1);
        int startDay = cal.get(Calendar.DAY_OF_WEEK);
        int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        int row = 0;
        for (int day = 1; day <= numberOfDays; day++) {
            int column = (startDay - 1 + day - 1) % 7;
            calendarTable.setValueAt(String.valueOf(day), row, column);
            if (column == 6) row++;
        }
    }

    // --- MAIN METHOD (FIXED INTERNAL NAMES) ---
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            MyCalendar calendar = new MyCalendar(); // Fixed name here
            calendar.setVisible(true);
        });
    }
}