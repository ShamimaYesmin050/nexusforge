package nexusforge.reports;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import nexusforge.databasequeries.DBConnection;

public class ReportExporter {

    public static boolean exportAttendanceToCSV(String absoluteFilePath) {
        String sql = "SELECT employee_id, date, check_in, check_out, status FROM attendance ORDER BY date DESC, employee_id ASC";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(absoluteFilePath));
             Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            writer.write("Employee ID,Date,Check In,Check Out,Status");
            writer.newLine();

            while (rs.next()) {
                String employeeId = rs.getString("employee_id");
                Date date = rs.getDate("date");
                Time checkIn = rs.getTime("check_in");
                Time checkOut = rs.getTime("check_out");
                String status = rs.getString("status");

                String empIdStr = (employeeId != null) ? employeeId : "";
                String dateStr = (date != null) ? date.toString() : "";
                String checkInStr = (checkIn != null) ? checkIn.toString() : "";
                String checkOutStr = (checkOut != null) ? checkOut.toString() : "N/A";
                String statusStr = (status != null) ? status : "";

                String line = String.format("%s,%s,%s,%s,%s", empIdStr, dateStr, checkInStr, checkOutStr, statusStr);
                writer.write(line);
                writer.newLine();
            }

            writer.flush();
            return true;

        } catch (IOException | SQLException e) {
            System.err.println("Error during CSV export: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
