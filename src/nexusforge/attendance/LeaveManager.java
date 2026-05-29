package nexusforge.attendance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import nexusforge.databasequeries.DBConnection;

public class LeaveManager {

    public boolean applyLeave(String employeeId, LocalDate startDate, LocalDate endDate, String reason, String leaveType) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            return false;
        }

        String sql = "INSERT INTO leave_requests (employee_id, start_date, end_date, reason, leave_type, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employeeId);
            stmt.setDate(2, Date.valueOf(startDate));
            stmt.setDate(3, Date.valueOf(endDate));
            stmt.setString(4, reason);
            stmt.setString(5, leaveType);
            stmt.setString(6, "Pending");

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String[]> getPendingLeaves() {
        List<String[]> pendingLeaves = new ArrayList<>();
        String sql = "SELECT id, employee_id, start_date, end_date, leave_type, reason FROM leave_requests WHERE status = 'Pending'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] row = new String[6];
                row[0] = String.valueOf(rs.getInt("id"));
                row[1] = rs.getString("employee_id");

                Date start = rs.getDate("start_date");
                row[2] = (start != null) ? start.toString() : "";

                Date end = rs.getDate("end_date");
                row[3] = (end != null) ? end.toString() : "";

                row[4] = rs.getString("leave_type");
                row[5] = rs.getString("reason");

                pendingLeaves.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pendingLeaves;
    }

    public boolean updateLeaveStatus(int requestId, String newStatus) {
        String sql = "UPDATE leave_requests SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, requestId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
