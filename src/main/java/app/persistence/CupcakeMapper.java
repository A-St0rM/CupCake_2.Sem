package app.persistence;

import app.DTO.CupcakeDTO;
import app.entities.Cupcake;
import app.exceptions.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CupcakeMapper {
    private final ConnectionPool connectionPool;

    public CupcakeMapper(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    public int insertCupcakeAndReturnId(Cupcake cupcake) {
        String sql = "INSERT INTO cupcakes (cupcake_top_id, cupcake_bottom_id, cupcake_price, quantity) VALUES (?, ?, ?, ?) RETURNING cupcake_id";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cupcake.getCupcakeTopId());
            ps.setInt(2, cupcake.getCupcakeBottomId());
            ps.setInt(3, cupcake.getPrice());
            ps.setInt(4, cupcake.getQuantity());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("cupcake_id");
            } else {
                throw new DatabaseException("Failed to insert cupcake and return ID");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not insert cupcake: " + e.getMessage());
        }
    }

    public List<CupcakeDTO> getAllCupcakesDTO() throws DatabaseException {
        String sql = "SELECT c.cupcake_id, t.top_name AS top_name, b.bottom_name AS bottom_name, c.cupcake_price, c.quantity " +
                "FROM cupcakes c " +
                "JOIN cupcake_tops t ON c.cupcake_top_id = t.cupcake_top_id " +
                "JOIN cupcake_bottoms b ON c.cupcake_bottom_id = b.cupcake_bottom_id";

        List<CupcakeDTO> cupcakeList = new ArrayList<>();

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                CupcakeDTO cupcakeDTO = new CupcakeDTO(
                        rs.getInt("cupcake_id"),
                        rs.getString("top_name"),
                        rs.getString("bottom_name"),
                        rs.getInt("cupcake_price"),
                        rs.getInt("quantity")
                );
                cupcakeList.add(cupcakeDTO);
            }
            return cupcakeList;
        } catch (SQLException e) {
            throw new DatabaseException("Could not get all cupcake Bottoms", e.getMessage());
        }
    }


    public boolean deleteCupcakeById(int cupcakeTopId) throws DatabaseException {
        String query = "DELETE FROM cupcakes WHERE cupcake_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        )
        {

            preparedStatement.setInt(1, cupcakeTopId);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0; // If at least one row was deleted, return true

        } catch (SQLException e) {
            throw new DatabaseException("Could not delete cupcake top: " + e.getMessage());
        }
    }


    public boolean updateCupcakeById(int cupcakeId, int quantity) throws DatabaseException
    {
        String query = "UPDATE cupcakes SET quantity = ? WHERE cupcake_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        )
        {
            ps.setInt(1, quantity);
            ps.setInt(2, cupcakeId);


            int affectedRows = ps.executeUpdate();

            return affectedRows > 0; // If at least one row was updated, return true
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not update cupcake bottom: " + e.getMessage());
        }
    }

    public Cupcake getCupcakeById(int cupcakeId) throws DatabaseException {
        String sql = "SELECT * FROM cupcakes WHERE cupcake_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, cupcakeId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Cupcake(
                        rs.getInt("cupcake_id"),
                        rs.getInt("cupcake_top_id"),
                        rs.getInt("cupcake_bottom_id"),
                        rs.getInt("cupcake_price"),
                        rs.getInt("quantity")
                );
            } else {
                return null; // Cupcake not found
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not get cupcake by ID: " + e.getMessage());
        }
    }

    public List<CupcakeDTO> getCupcakesByOrderlineId(int orderlineId) throws DatabaseException {
        String sql = """
        SELECT c.cupcake_id, t.top_name, b.bottom_name, c.cupcake_price, c.quantity
        FROM cupcakes c
        JOIN cupcake_tops t ON c.cupcake_top_id = t.cupcake_top_id
        JOIN cupcake_bottoms b ON c.cupcake_bottom_id = b.cupcake_bottom_id
        JOIN cupcakes_orderlines co ON c.cupcake_id = co.cupcake_id
        WHERE co.orderline_id = ?
    """;

        List<CupcakeDTO> cupcakes = new ArrayList<>();

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderlineId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cupcakes.add(new CupcakeDTO(
                        rs.getInt("cupcake_id"),
                        rs.getString("top_name"),
                        rs.getString("bottom_name"),
                        rs.getInt("cupcake_price"),
                        rs.getInt("quantity")
                ));
            }
            return cupcakes;
        } catch (SQLException e) {
            throw new DatabaseException("Could not get cupcakes by orderline ID", e.getMessage());
        }
    }



}
