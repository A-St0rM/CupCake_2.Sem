package app.persistence;

import app.DTO.AdminDTO;
import app.entities.Admin;
import app.entities.Customer;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMapper {

    private final ConnectionPool connectionPool;

    public AdminMapper(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    public AdminDTO login(String email, String password) throws SQLException {
        // Denne SQL linje konverterer input parametrene til lowercase og sammenligner med email i lowercase
        String sql = "SELECT * FROM admins WHERE LOWER(email) = LOWER(?) and password = ?";

        Connection connection = connectionPool.getConnection();

        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("admin_id");
                String adminEmail = rs.getString("email");
                return new AdminDTO(id, adminEmail);
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen");
            }
        } catch (SQLException e) {
            System.err.println("SQL Fejl" + e.getMessage());
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public void createAdmin(String email, String password) throws DatabaseException
    {
        String sql = "INSERT INTO admins (email, password) VALUES (?,?)";

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

    public void addToCustomerBalance(int customer_id, int balance) throws DatabaseException {
        String sql = "UPDATE customers SET balance = balance + ? WHERE customer_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, balance);
            ps.setInt(2, customer_id);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        } catch (SQLException e) {
            throw new DatabaseException("Error updating balance");
        }

    }

    public void deleteAdminById(int admin_id) throws DatabaseException
    {
        String sql = "delete from admins where admin_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setInt(1, admin_id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl i fjernelse af admin");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl ved sletning af admin", e.getMessage());
        }
    }

    public void updateAdminById(int admin_id, String email) throws DatabaseException
    {
        String sql = "update admins set email = ? where admin_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(1, email);
            ps.setInt(2, admin_id);
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



