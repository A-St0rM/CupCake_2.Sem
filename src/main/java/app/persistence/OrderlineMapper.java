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

    public void insertOrderline(Orderline orderline) throws DatabaseException {
        String sql = "INSERT INTO orderlines (orderline_id, order_id, initial_price) VALUES (?, ?, ?)";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderline.getOrderlineId());
            ps.setInt(2, orderline.getOrderId());
            ps.setDouble(3, orderline.getInitialPrice());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Could not insert new Orderline: " + e.getMessage());
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
                        rs.getDouble("initial_price")
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

    public double getOrderlinePriceById(int orderlineId) throws DatabaseException {
        String sql = "SELECT initial_price FROM orderlines WHERE orderline_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderlineId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("initial_price");
            } else {
                throw new DatabaseException("Orderline not found.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not get orderline price: " + e.getMessage());
        }
    }



    public boolean updateOrderlineById(int orderlineId, double newPrice) throws DatabaseException
    {
        String query = "UPDATE orderlines SET initial_price = ? WHERE orderline_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        )
        {
            ps.setDouble(1, newPrice);
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
}
