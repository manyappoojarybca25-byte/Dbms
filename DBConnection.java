/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospitalappointmentsystems;

/**
 *
 * @author LENOVO Manya P Poojary
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/hospital_appointment_db";
            String user = "root";
            String password = "DBMS@2026";

            return DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}