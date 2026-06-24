import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.YearMonth;

public class CalendarApp extends JFrame {

    private JLabel monthLabel;
    private JPanel calendarPanel;
    private YearMonth currentMonth;
    private LocalDate selectedDate;   // NEW: selected date tracking

    public CalendarApp() {
        setTitle("Calendar Application");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        currentMonth = YearMonth.now();
        selectedDate = null;

        setLayout(new BorderLayout());

        // ===== TOP PANEL (NAVIGATION) =====
        JPanel topPanel = new JPanel(new BorderLayout());

        JButton prevButton = new JButton("< Prev");
        JButton nextButton = new JButton("Next >");

        monthLabel = new JLabel("", JLabel.CENTER);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 18));

        prevButton.addActionListener(e -> {
            currentMonth = currentMonth.minusMonths(1);
            refreshCalendar();
        });

        nextButton.addActionListener(e -> {
            currentMonth = currentMonth.plusMonths(1);
            refreshCalendar();
        });

        topPanel.add(prevButton, BorderLayout.WEST);
        topPanel.add(monthLabel, BorderLayout.CENTER);
        topPanel.add(nextButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // ===== CALENDAR PANEL =====
        calendarPanel = new JPanel();
        add(calendarPanel, BorderLayout.CENTER);

        refreshCalendar();
    }

    private void refreshCalendar() {

        calendarPanel.removeAll();
        calendarPanel.setLayout(new GridLayout(0, 7));

        monthLabel.setText(currentMonth.getMonth() + " " + currentMonth.getYear());

        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        for (String d : days) {
            JLabel label = new JLabel(d, JLabel.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            calendarPanel.add(label);
        }

        LocalDate firstDay = currentMonth.atDay(1);
        int startDay = firstDay.getDayOfWeek().getValue() % 7;
        int totalDays = currentMonth.lengthOfMonth();

        LocalDate today = LocalDate.now();   // NEW: current date

        // empty cells before start
        for (int i = 0; i < startDay; i++) {
            calendarPanel.add(new JLabel(""));
        }

        // days
        for (int day = 1; day <= totalDays; day++) {

            LocalDate date = currentMonth.atDay(day);

            JLabel dayLabel = new JLabel(String.valueOf(day), JLabel.CENTER);
            dayLabel.setOpaque(true);
            dayLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            // ===== Highlight TODAY =====
            if (date.equals(today)) {
                dayLabel.setBackground(Color.ORANGE);
            }

            // ===== Highlight SELECTED DATE =====
            if (selectedDate != null && date.equals(selectedDate)) {
                dayLabel.setBackground(Color.CYAN);
            }

            // ===== Click to select date =====
            dayLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedDate = date;
                    refreshCalendar();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    dayLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            });

            calendarPanel.add(dayLabel);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalendarApp().setVisible(true);
        });
    }
}