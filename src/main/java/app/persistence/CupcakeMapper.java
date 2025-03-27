package app.persistence;

import app.entities.Cupcake;
import java.sql.*;


public class CupcakeMapper {

    public static void saveCupcake(Cupcake cupcake) {
        String sql = "INSERT INTO cupcakes (cupcake_top_id, cupcake_bottom_id, cupcake_price) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cupcake.getCupcakeTopId());
            ps.setInt(2, cupcake.getCupcakeBottomId());
            ps.setDouble(3, cupcake.getPrice());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
