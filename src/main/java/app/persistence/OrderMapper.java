package app.persistence;

import app.entities.Order;
import app.entities.Orderline;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    private final ConnectionPool connectionPool;

    public OrderMapper(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    public void insertOrder(Order order) throws DatabaseException {
        String sql = "INSERT INTO orders(order_id, customer_id, order_date, total_price, status_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, order.getOrderId());
            ps.setInt(2, order.getCustomerId());
            ps.setDate(3, Date.valueOf(order.getOrderDate()));
            ps.setDouble(4, order.getTotalPrice());
            ps.setInt(5, order.getStatusId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Could not insert new Order: " + e.getMessage());
        }
    }

    public List<Order> getAllOrders() throws DatabaseException {
        String sql = "SELECT * FROM orders";

        List<Order> orderList = new ArrayList<>();

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getDouble("total_price"),
                        rs.getInt("status_id")
                );
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new DatabaseException("Could not get all orders: " + e.getMessage());
        }
    }


    public boolean deleteOrderById(int orderId) throws DatabaseException {
        String query = "DELETE FROM orders WHERE order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        )
        {

            preparedStatement.setInt(1, orderId);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0; // If at least one row was deleted, return true

        } catch (SQLException e) {
            throw new DatabaseException("Could not delete order: " + e.getMessage());
        }
    }


    public boolean doesOrderExist(int orderId){

        String query = "SELECT * FROM orders WHERE order_id = ?";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ){
            preparedStatement.setInt(1, orderId);
            ResultSet rs = preparedStatement.executeQuery();

            return rs.next(); //If true the order exists

        } catch (SQLException e) {
            throw new DatabaseException("Could not check if the order exists: " + e.getMessage());
        }
    }

    public boolean updateOrderById(int orderId) throws DatabaseException
    {
        String query = "UPDATE orders SET total_price = (SELECT SUM(initial_price) FROM orderlines WHERE order_id = ?) WHERE order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        )
        {
            ps.setInt(1, orderId);
            ps.setInt(2, orderId);


            int affectedRows = ps.executeUpdate();

            return affectedRows > 0; // If at least one row was updated, return true
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not update order: " + e.getMessage());
        }
    }
}
