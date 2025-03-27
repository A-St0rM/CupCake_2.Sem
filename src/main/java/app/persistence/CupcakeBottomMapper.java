package app.persistence;

import app.entities.CupcakeBottom;
import app.entities.CupcakeTop;
import app.exceptions.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CupcakeBottomMapper {

    private final ConnectionPool connectionPool;

    public CupcakeBottomMapper(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    public CupcakeBottom insertCupcakeBottom(CupcakeBottom cupcakeBottom) throws DatabaseException {
        String query = "INSERT INTO cupcake_bottoms (price, bottom_name)   VALUES(?,?)";

        try (Connection connection = connectionPool.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){

                preparedStatement.setDouble(1, cupcakeBottom.getPrice());
                preparedStatement.setString(2, cupcakeBottom.getCupcake_bottom_name());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new DatabaseException("Creating cupcake bottom failed, no rows affected.");
                }

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);

                        // Return a new cupcake object including the generated ID
                        return new CupcakeBottom(generatedId, cupcakeBottom.getPrice(), cupcakeBottom.getCupcake_bottom_name());
                    } else {
                        throw new DatabaseException("Creating cupcake bottom failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not create cupcake bottom" + e.getMessage());
        }
    }


    public double getPriceById(int bottomId) {
        String sql = "SELECT price FROM cupcake_bottoms WHERE cupcake_bottom_id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bottomId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public CupcakeTop getCupcakeBottomById(int id) throws DatabaseException {
        String sql = "SELECT * FROM cupcake_bottoms WHERE cupcake_bottom_id = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CupcakeTop(rs.getInt("cupcake_bottom_id"), rs.getDouble("price"), rs.getString("bottom_name"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not get Cupcake by id: " + id + e.getMessage());
        }
        return null;
    }

    public List<CupcakeBottom> getAllCupcakeBottoms() throws DatabaseException {
        String sql = "SELECT cupcake_bottom_id, price, bottom_name FROM cupcake_bottoms";

        List<CupcakeBottom> cupcakeBottomList = new ArrayList<>();

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                CupcakeBottom cupcakeBottom = new CupcakeBottom(
                        rs.getInt("cupcake_bottom_id"),
                        rs.getDouble("price"),
                        rs.getString("bottom_name")
                );
                cupcakeBottomList.add(cupcakeBottom);
            }
                return cupcakeBottomList;
        } catch (SQLException e) {
            throw new DatabaseException("Could not get all cupcake Bottoms", e.getMessage());
        }
    }


    public boolean deleteCupcakeBottom(int cupcakeBottomId) throws DatabaseException {
        String query = "DELETE FROM cupcake_bottoms WHERE cupcake_bottom_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cupcakeBottomId);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0; // If at least one row was deleted, return true

        } catch (SQLException e) {
            throw new DatabaseException("Could not delete cupcake bottom: " + e.getMessage());
        }
    }

    public boolean updateCupcakeBottomById(int cupcakeBottomId, String newName, double newPrice) throws DatabaseException
    {
        String query = "UPDATE cupcake_bottoms SET bottom_name = ?, price = ? WHERE cupcake_bottom_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        )
        {
            ps.setString(1, newName);
            ps.setDouble(2, newPrice);
            ps.setInt(3, cupcakeBottomId);


            int affectedRows = ps.executeUpdate();

            return affectedRows > 0; // If at least one row was updated, return true
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not update cupcake bottom: " + e.getMessage());
        }
    }
}
