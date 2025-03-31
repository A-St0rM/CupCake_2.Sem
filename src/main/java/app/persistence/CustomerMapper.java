package app.persistence;

import app.DTO.CustomerDTO;
import app.entities.Customer;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}

