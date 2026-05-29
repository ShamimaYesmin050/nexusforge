package nexusforge.attendance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import nexusforge.databasequeries.DBConnection;

public class AttendanceController {

    public boolean checkIn(String employeeId) {
        LocalDate today = LocalDate.now();
        String selectSql = "SELECT 1 FROM attendance WHERE employee_id = ? AND date = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            selectStmt.setString(1, employeeId);
            selectStmt.setDate(2, Date.valueOf(today));

            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            }

            LocalTime nowTime = LocalTime.now();
            String status = nowTime.isAfter(LocalTime.of(9, 0, 0)) ? "Late" : "Present";

            String insertSql = "INSERT INTO attendance (employee_id, date, check_in, status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, employeeId);
                insertStmt.setDate(2, Date.valueOf(today));
                insertStmt.setTime(3, Time.valueOf(nowTime));
                insertStmt.setString(4, status);

                int rowsInserted = insertStmt.executeUpdate();
                return rowsInserted > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkOut(String employeeId) {
        LocalDate today = LocalDate.now();
        String selectSql = "SELECT 1 FROM attendance WHERE employee_id = ? AND date = ? AND check_out IS NULL";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            selectStmt.setString(1, employeeId);
            selectStmt.setDate(2, Date.valueOf(today));

            try (ResultSet rs = selectStmt.executeQuery()) {
                if (!rs.next()) {
                    return false;
                }
            }

            LocalTime nowTime = LocalTime.now();
            String updateSql = "UPDATE attendance SET check_out = ? WHERE employee_id = ? AND date = ? AND check_out IS NULL";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setTime(1, Time.valueOf(nowTime));
                updateStmt.setString(2, employeeId);
                updateStmt.setDate(3, Date.valueOf(today));

                int rowsUpdated = updateStmt.executeUpdate();
                return rowsUpdated > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
