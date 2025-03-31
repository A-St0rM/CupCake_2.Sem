package app.persistence;

import app.DTO.CustomerOrderDTO;
import app.DTO.OrderStatusDTO;
import app.entities.Order;
import app.entities.Orderline;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    private final ConnectionPool connectionPool;

    public OrderMapper(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    public int insertOrder(Order order) throws DatabaseException {
        String sql = "INSERT INTO orders (customer_id, order_date, total_price, status_id) " +
                "VALUES (?, ?, ?, ?) RETURNING order_id";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, order.getCustomerId());
            ps.setDate(2, Date.valueOf(order.getOrderDate()));
            ps.setInt(3, order.getTotalPrice());
            ps.setInt(4, order.getStatusId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("order_id");
            } else {
                throw new DatabaseException("Failed to insert order and retrieve ID.");
            }

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
                        rs.getInt("total_price"),
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

    public boolean updateOrderById(int orderId) throws DatabaseException {
        String query = "UPDATE orders SET total_price = (SELECT SUM(initial_price) FROM orderlines WHERE order_id = ?) WHERE order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        ) {
            ps.setInt(1, orderId);
            ps.setInt(2, orderId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Could not update order: " + e.getMessage());
        }
    }

    public List<CustomerOrderDTO> getOrdersWithCustomerInfo() throws DatabaseException {
        List<CustomerOrderDTO> orders = new ArrayList<>();

        String sql = "SELECT o.order_id, c.email, o.total_price, o.order_date " +
                "FROM orders o " +
                "JOIN customers c ON o.customer_id = c.customer_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String customerEmail = rs.getString("email");
                double totalPrice = rs.getDouble("total_price");
                LocalDate orderDate = rs.getDate("order_date").toLocalDate();

                orders.add(new CustomerOrderDTO(orderId, customerEmail, totalPrice, orderDate));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting orders", e.getMessage());
        }
        return orders;
    }

    public List<OrderStatusDTO> getOrdersWithStatus() throws DatabaseException {
        List<OrderStatusDTO> ordersWithStatus = new ArrayList<>();

        String sql = "SELECT o.order_id, o.customer_id, o.order_date, o.total_price, " +
                "s.is_paid, s.is_picked_up " +
                "FROM orders o " +
                "JOIN status s ON o.status_id = s.status_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int customerId = rs.getInt("customer_id");
                LocalDate orderDate = rs.getDate("order_date").toLocalDate();
                double totalPrice = rs.getDouble("total_price");
                boolean isPaid = rs.getBoolean("is_paid");
                boolean isPickedUp = rs.getBoolean("is_picked_up");

                ordersWithStatus.add(new OrderStatusDTO(orderId, customerId, orderDate, totalPrice, isPaid, isPickedUp));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting orders with status: " + e.getMessage());
        }
        return ordersWithStatus;
    }

    public int getLatestOrderIdByCustomer(int customerId) {
        String sql = "SELECT order_id FROM orders WHERE customer_id = ? ORDER BY order_date DESC, order_id DESC LIMIT 1";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("order_id");
            } else {
                throw new DatabaseException("No order found for customer: " + customerId);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Could not get latest order ID: " + e.getMessage());
        }
    }

}
