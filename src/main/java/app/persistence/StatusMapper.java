package app.persistence;

import app.exceptions.DatabaseException;

import java.sql.*;

public class StatusMapper {

    private final ConnectionPool connectionPool;

    public StatusMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public int createStatus() throws DatabaseException {
        String sql = "INSERT INTO status (is_paid, is_picked_up) VALUES (false, false)";

        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1); // return the generated status_id
            } else {
                throw new DatabaseException("Failed to retrieve generated status ID");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not create status: " + e.getMessage());
        }
    }

    // Update status for payment
    public boolean updatePaymentStatus(int statusId, boolean isPaid) throws DatabaseException {
        String sql = "UPDATE status SET is_paid = ? WHERE status_id = ?";

        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setBoolean(1, isPaid);
            ps.setInt(2, statusId);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0; // Returns true if at least one row was updated
        } catch (SQLException e) {
            throw new DatabaseException("Could not update payment status: " + e.getMessage());
        }
    }

    public void updatePickupStatus(int orderId, boolean isPickedUp) throws DatabaseException {
        String sql = "UPDATE status SET is_picked_up = ? WHERE status_id = (SELECT status_id FROM orders WHERE order_id = ?)";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, isPickedUp);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Could not update pickup status: " + e.getMessage());
        }
    }

    public boolean getPaymentStatus(int statusId) throws DatabaseException {
        String sql = "SELECT is_paid FROM status WHERE status_id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, statusId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_paid");
            }
            throw new DatabaseException("Status not found");
        } catch (SQLException e) {
            throw new DatabaseException("Could not fetch payment status: " + e.getMessage());
        }
    }

    public boolean isPaidStatusByOrderId(int orderId) throws DatabaseException {
        String sql = """
        SELECT s.is_paid
        FROM orders o
        JOIN status s ON o.status_id = s.status_id
        WHERE o.order_id = ?
    """;

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("is_paid");
            } else {
                throw new DatabaseException("No status found for order ID: " + orderId);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error checking paid status for order: " + e.getMessage());
        }
    }
}


