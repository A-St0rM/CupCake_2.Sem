package app.persistence;

import app.DTO.CustomerDTO;
import app.DTO.PurchaseOverviewDTO;
import app.entities.Customer;
import app.exceptions.DatabaseException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {
    private final ConnectionPool connectionPool;

    public CustomerMapper(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    public CustomerDTO login(String email, String password) throws SQLException {
        // Denne super fede SQL linje konverterer input parametrene (ps) til lowercase og sammenligner med email i lowercase
        String sql = "SELECT * FROM customers WHERE LOWER(email) = LOWER(?) AND password = ?";


        Connection connection = connectionPool.getConnection();

        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("customer_id");
                double balance = rs.getDouble("balance");
                String customerEmail = rs.getString("email");
                return new CustomerDTO(id, customerEmail, balance);
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen");
            }
        } catch (SQLException e) {
            System.err.println("SQL Fejl" + e.getMessage());
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public void createCustomer(String email, String password) throws DatabaseException
    {
        String sql = "insert into customers (email, password, balance) values (?,?, 0.0)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(1, email);
            ps.setString(2, password);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl ved oprettelse af ny bruger");
            }
        }
        catch (SQLException e)
        {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value "))
            {
                msg = "Brugernavnet findes allerede. Vælg et andet";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
    }

    public void deleteCustomerById(int userId) throws DatabaseException
    {
        String sql = "delete from customers where customer_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setInt(1, userId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl i fjernelse af bruger");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl ved sletning af bruger", e.getMessage());
        }
    }

    public List<PurchaseOverviewDTO> getPurchaseOverviewByCustomerId(int customerId) {
        List<PurchaseOverviewDTO> overviewList = new ArrayList<>();

        String sql = """
            SELECT b.bottom_name, t.top_name, c.quantity, (c.cupcake_price * c.quantity) AS total_price,
                   s.is_paid, s.is_picked_up
            FROM customers cu
            JOIN orders o ON cu.customer_id = o.customer_id
            JOIN status s ON o.status_id = s.status_id
            JOIN orderlines ol ON o.order_id = ol.order_id
            JOIN cupcakes_orderlines co ON ol.orderline_id = co.orderline_id
            JOIN cupcakes c ON co.cupcake_id = c.cupcake_id
            JOIN cupcake_tops t ON c.cupcake_top_id = t.cupcake_top_id
            JOIN cupcake_bottoms b ON c.cupcake_bottom_id = b.cupcake_bottom_id
            WHERE cu.customer_id = ?
            ORDER BY o.order_date DESC;
            """;

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PurchaseOverviewDTO dto = new PurchaseOverviewDTO(
                        rs.getString("bottom_name"),
                        rs.getString("top_name"),
                        rs.getInt("quantity"),
                        rs.getInt("total_price"),
                        rs.getBoolean("is_paid"),
                        rs.getBoolean("is_picked_up")
                );
                overviewList.add(dto);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not get purchase overview: " + e.getMessage());
        }

        return overviewList;
    }


    public void updateCustomerById(int customerId, String email) throws DatabaseException
    {
        String sql = "update customers set email = ? where customer_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(1, email);
            ps.setInt(2, customerId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl i opdatering af email");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl i opdatering af email, prøv igen", e.getMessage());
        }
    }

    public BigDecimal getBalanceByCustomerId(int customerId) {
        String sql = "SELECT balance FROM customers WHERE customer_id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("balance");
            } else {
                throw new DatabaseException("Customer not found: " + customerId);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not get balance: " + e.getMessage());
        }
    }

    public boolean updateCustomerBalance(int customerId, BigDecimal newBalance) {
        String sql = "UPDATE customers SET balance = ? WHERE customer_id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, newBalance);
            ps.setInt(2, customerId);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Could not update customer balance: " + e.getMessage());
        }
    }

    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customers = new ArrayList<>();

        String sql = "SELECT customer_id, email, balance FROM customers ORDER BY email ASC";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("customer_id");
                String email = rs.getString("email");
                double balance = rs.getDouble("balance");

                customers.add(new CustomerDTO(id, email, balance));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Could not fetch customers: " + e.getMessage());
        }

        return customers;
    }

}

