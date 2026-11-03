/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hospitalappointmentsystems;

/**
 *
 * @author Spoorthi K P
 */
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HospitalAppointmentSystems extends JFrame {

    JTextField id, patientName, age, gender, doctorName;
    JTextField appointmentDate, appointmentTime, reason;

    JTable table;
    DefaultTableModel model;

    Color background = new Color(247, 240, 232);
    Color mocha = new Color(139, 111, 92);
    Color darkBrown = new Color(112, 87, 71);
    Color beige = new Color(229, 213, 197);
    Color cream = new Color(255, 255, 255);

    public HospitalAppointmentSystems() {

        setTitle("Hospital Appointment Management System");
        setSize(1100, 750);        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(15, 15));

        getContentPane().setBackground(background);

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(mocha);
        header.setBorder(BorderFactory.createEmptyBorder(18, 10, 18, 10));

        JLabel title = new JLabel(
                "HOSPITAL APPOINTMENT MANAGEMENT SYSTEM",
                SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 25));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Your Health, Our Priority",
                SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(cream);

        header.add(title);
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(background);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

   
        JPanel formPanel = new JPanel(new GridLayout(4, 4, 12, 12));
        formPanel.setBackground(background);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(beige, 2),
                "Appointment Details",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 17),
                darkBrown));

        id = new JTextField();
        patientName = new JTextField();
        age = new JTextField();
        gender = new JTextField();
        doctorName = new JTextField();
        appointmentDate = new JTextField();
        appointmentTime = new JTextField();
        reason = new JTextField();

        addField(formPanel, "Appointment ID:", id);
        addField(formPanel, "Patient Name:", patientName);
        addField(formPanel, "Age:", age);
        addField(formPanel, "Gender:", gender);
        addField(formPanel, "Doctor Name:", doctorName);
        addField(formPanel, "Date (YYYY-MM-DD):", appointmentDate);
        addField(formPanel, "Time:", appointmentTime);
        addField(formPanel, "Reason:", reason);

        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // BUTTONS
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        buttonPanel.setBackground(background);

        JButton addButton = createButton("Add Appointment");
        JButton viewButton = createButton("View Appointments");
        JButton updateButton = createButton("Update Appointment");
        JButton deleteButton = createButton("Delete Appointment");
        JButton resetButton = createButton("Reset All");

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(resetButton);

        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        // APPOINTMENT TABLE
        String[] columns = {
            "ID", "Patient", "Age", "Gender",
            "Doctor", "Date", "Time", "Reason"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setBackground(cream);
        table.setForeground(darkBrown);
        table.setSelectionBackground(beige);
        table.setSelectionForeground(darkBrown);
        table.setGridColor(beige);

        table.getTableHeader().setBackground(mocha);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(cream);
        scrollPane.setBorder(BorderFactory.createLineBorder(beige, 2));

        mainPanel.add(scrollPane);

        add(mainPanel, BorderLayout.CENTER);

        // BUTTON ACTIONS
        addButton.addActionListener(e -> addAppointment());
        viewButton.addActionListener(e -> viewAppointments());
        updateButton.addActionListener(e -> updateAppointment());
        deleteButton.addActionListener(e -> deleteAppointment());
        resetButton.addActionListener(e -> resetAppointments());

        setLocationRelativeTo(null);
        setVisible(true);

        viewAppointments();
    }

    // FORM FIELD DESIGN
    void addField(JPanel panel, String label, JTextField field) {

        JLabel jlabel = new JLabel(label);
        jlabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        jlabel.setForeground(darkBrown);

        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(cream);
        field.setForeground(darkBrown);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(beige),
                BorderFactory.createEmptyBorder(5, 7, 5, 7)));

        panel.add(jlabel);
        panel.add(field);
    }

    // BUTTON DESIGN
    JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(mocha);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    // ADD APPOINTMENT
    void addAppointment() {

        String sql = "INSERT INTO appointments "
                + "(patient_name, age, gender, doctor_name, "
                + "appointment_date, appointment_time, reason) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, patientName.getText().trim());
            ps.setInt(2, Integer.parseInt(age.getText().trim()));
            ps.setString(3, gender.getText().trim());
            ps.setString(4, doctorName.getText().trim());
            ps.setDate(5, Date.valueOf(
                    appointmentDate.getText().trim()));
            ps.setString(6, appointmentTime.getText().trim());
            ps.setString(7, reason.getText().trim());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Appointment Added Successfully!");

            clearFields();
            viewAppointments();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Add Error: " + e.getMessage());
        }
    }

    // VIEW APPOINTMENTS
    void viewAppointments() {

        model.setRowCount(0);

        String sql = "SELECT * FROM appointments ORDER BY appointment_id";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                model.addRow(new Object[]{
                    rs.getInt("appointment_id"),
                    rs.getString("patient_name"),
                    rs.getInt("age"),
                    rs.getString("gender"),
                    rs.getString("doctor_name"),
                    rs.getDate("appointment_date"),
                    rs.getString("appointment_time"),
                    rs.getString("reason")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "View Error: " + e.getMessage());
        }
    }

    // UPDATE APPOINTMENT
    void updateAppointment() {

        String sql = "UPDATE appointments SET patient_name=?, age=?, "
                + "gender=?, doctor_name=?, appointment_date=?, "
                + "appointment_time=?, reason=? WHERE appointment_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, patientName.getText().trim());
            ps.setInt(2, Integer.parseInt(age.getText().trim()));
            ps.setString(3, gender.getText().trim());
            ps.setString(4, doctorName.getText().trim());
            ps.setDate(5, Date.valueOf(
                    appointmentDate.getText().trim()));
            ps.setString(6, appointmentTime.getText().trim());
            ps.setString(7, reason.getText().trim());
            ps.setInt(8, Integer.parseInt(id.getText().trim()));

            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this,
                        "Appointment Updated Successfully!");
                clearFields();
                viewAppointments();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Appointment ID Not Found!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Update Error: " + e.getMessage());
        }
    }

    // DELETE APPOINTMENT
    void deleteAppointment() {

        try {
            int appointmentId =
                    Integer.parseInt(id.getText().trim());

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Delete appointment ID " + appointmentId + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            String sql =
                    "DELETE FROM appointments WHERE appointment_id=?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, appointmentId);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Appointment Deleted Successfully!");
                    clearFields();
                    viewAppointments();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Appointment ID Not Found!");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Delete Error: " + e.getMessage());
        }
    }

    // RESET ALL APPOINTMENTS
    void resetAppointments() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "This will permanently delete ALL appointments. Continue?",
                "Confirm Reset",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement()) {

            st.executeUpdate("TRUNCATE TABLE appointments");

            JOptionPane.showMessageDialog(this,
                    "All appointments have been deleted.");

            clearFields();
            viewAppointments();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Reset Error: " + e.getMessage());
        }
    }

    // CLEAR INPUT FIELDS
    void clearFields() {

        id.setText("");
        patientName.setText("");
        age.setText("");
        gender.setText("");
        doctorName.setText("");
        appointmentDate.setText("");
        appointmentTime.setText("");
        reason.setText("");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new HospitalAppointmentSystems());
    }
}