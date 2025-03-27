package app.persistence;

import app.entities.Customer;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper {

    public static Customer login(String email, String password, ConnectionPool connectionPool) throws SQLException {
        String sql = "select * from Customers where email = ? and password = ?";

        Connection connection = connectionPool.getConnection();

        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("customer_id");
                int balance = rs.getInt("role");
                return new Customer(id, email, password, balance);
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen");
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public static void createCustomer(String email, String password, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "insert into Customers (email, password) values (?,?)";

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

    public static void deleteCustomerById(int userId, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "delete from Customers where customer_id = ?";

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

    public static void updateCustomerById(int customerId, String email, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "update Customers set email = ? where customer_id = ?";

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

