package app.persistence;

import app.entities.CupcakeTop;
import app.exceptions.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CupcakeTopMapper {

    private final ConnectionPool connectionPool;

    public CupcakeTopMapper(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    public CupcakeTop insertCupcakeTop(CupcakeTop cupcakeTop) throws DatabaseException {
        String query = "INSERT INTO cupcake_tops (price, top_name)   VALUES(?,?)";

        try (Connection connection = connectionPool.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){

                preparedStatement.setDouble(1, cupcakeTop.getPrice());
                preparedStatement.setString(2, cupcakeTop.getCupcakeTopName());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new DatabaseException("Creating cupcake top failed, no rows affected.");
                }

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);

                        // Return a new cupcake object including the generated ID
                        return new CupcakeTop(generatedId, cupcakeTop.getPrice(), cupcakeTop.getCupcakeTopName());
                    } else {
                        throw new DatabaseException("Creating cupcake top failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not create cupcake top" + e.getMessage());
        }
    }


    public List<CupcakeTop> getAllCupcakeTops() throws DatabaseException {
        String sql = "SELECT cupcake_top_id, price, top_name FROM cupcake_tops";

        List<CupcakeTop> cupcakeTopList = new ArrayList<>();

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                CupcakeTop cupcakeTop = new CupcakeTop(
                        rs.getInt("cupcake_top_id"),
                        rs.getInt("price"),
                        rs.getString("top_name")
                );
                cupcakeTopList.add(cupcakeTop);
            }
            return cupcakeTopList;
        } catch (SQLException e) {
            throw new DatabaseException("Could not get all cupcake tops", e.getMessage());
        }
    }

    public CupcakeTop getCupcakeTopById(int id) throws DatabaseException {
        String sql = "SELECT * FROM cupcake_tops WHERE cupcake_top_id = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CupcakeTop(rs.getInt("cupcake_top_id"), rs.getInt("price"), rs.getString("top_name"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not get Cupcake by id: " + id + e.getMessage());
        }
        return null;
    }


    public int getPriceById(int topId) {
        String sql = "SELECT price FROM cupcake_tops WHERE cupcake_top_id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, topId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean deleteCupcakeTop(int cupcakeTopId) throws DatabaseException {
        String query = "DELETE FROM cupcake_tops WHERE cupcake_top_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cupcakeTopId);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0; // If at least one row was deleted, return true

        } catch (SQLException e) {
            throw new DatabaseException("Could not delete cupcake top: " + e.getMessage());
        }
    }

    public boolean updateCupcakeTopById(int cupcakeTopId, String newName, double newPrice) throws DatabaseException
    {
        String query = "UPDATE cupcake_tops SET top_name = ?, price = ? WHERE cupcake_top_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        )
        {
            ps.setString(1, newName);
            ps.setDouble(2, newPrice);
            ps.setInt(3, cupcakeTopId);


            int affectedRows = ps.executeUpdate();

            return affectedRows > 0; // If at least one row was updated, return true
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not update cupcake top: " + e.getMessage());
        }
    }

}
