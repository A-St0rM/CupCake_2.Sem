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
}


