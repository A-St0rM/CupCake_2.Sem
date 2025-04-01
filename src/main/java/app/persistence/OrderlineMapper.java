package app.persistence;

import app.entities.Orderline;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderlineMapper {

    private final ConnectionPool connectionPool;

    public OrderlineMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public int insertOrderline(Orderline orderline) throws DatabaseException {
        String sql = "INSERT INTO orderlines (order_id, initial_price) VALUES (?, ?) RETURNING orderline_id";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderline.getOrderId());
            ps.setInt(2, orderline.getInitialPrice());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("orderline_id");
            } else {
                throw new DatabaseException("Failed to insert orderline and retrieve ID.");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Could not insert new Orderline: " + e.getMessage());
        }
    }


    public void insertCupcakeToOrderline(int cupcakeId, int orderlineId) {
        String sql = "INSERT INTO cupcakes_orderlines (cupcake_id, orderline_id) VALUES (?, ?)";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cupcakeId);
            ps.setInt(2, orderlineId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Could not insert cupcake into orderline: " + e.getMessage());
        }
    }

    public List<Orderline> getAllOrderlines() throws DatabaseException {
        String sql = "SELECT * FROM orderlines";

        List<Orderline> orderlineList = new ArrayList<>();

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Orderline orderline = new Orderline(
                        rs.getInt("orderline_id"),
                        rs.getInt("order_id"),
                        rs.getInt("initial_price")
                );
                orderlineList.add(orderline);
            }
            return orderlineList;
        } catch (SQLException e) {
            throw new DatabaseException("Could not get all orderlines: " + e.getMessage());
        }
    }


    public boolean deleteOrderlinesById(int orderlineId) throws DatabaseException {
        String query = "DELETE FROM orderlines WHERE orderline_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        )
        {

            preparedStatement.setInt(1, orderlineId);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0; // If at least one row was deleted, return true

        } catch (SQLException e) {
            throw new DatabaseException("Could not delete orderline: " + e.getMessage());
        }
    }

    public int getOrderlinePriceById(int orderlineId) throws DatabaseException {
        String sql = "SELECT initial_price FROM orderlines WHERE orderline_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderlineId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("initial_price");
            } else {
                throw new DatabaseException("Orderline not found.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not get orderline price: " + e.getMessage());
        }
    }



    public boolean updateOrderlineById(int orderlineId, int newPrice) throws DatabaseException
    {
        String query = "UPDATE orderlines SET initial_price = ? WHERE orderline_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        )
        {
            ps.setInt(1, newPrice);
            ps.setInt(2, orderlineId);


            int affectedRows = ps.executeUpdate();

            return affectedRows > 0; // If at least one row was updated, return true
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not update orderline: " + e.getMessage());
        }
    }


    public int getOrderIdByOderlineId(int orderlineId){
        String query = "SELECT order_id FROM orderlines WHERE orderline_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        )
        {
            ps.setInt(1, orderlineId);
            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("order_id");
            }
            else{
                throw new DatabaseException("No order found for this orderline");
            }

        }
        catch (SQLException e)
        {
            throw new DatabaseException("Error getting order ID: " + e.getMessage());
        }
    }

    public int getOrderlineIdByOrderId(int orderId) {
        String sql = "SELECT orderline_id FROM orderlines WHERE order_id = ? LIMIT 1";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("orderline_id");
            } else {
                throw new DatabaseException("No orderline found for order: " + orderId);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Could not get orderline ID: " + e.getMessage());
        }
    }
}
