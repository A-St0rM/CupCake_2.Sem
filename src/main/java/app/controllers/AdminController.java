package app.controllers;

import app.DTO.AdminDTO;
import app.DTO.CustomerDTO;
import app.entities.Admin;
import app.entities.Customer;
import app.exceptions.DatabaseException;
import app.persistence.AdminMapper;
import app.persistence.ConnectionPool;
import app.persistence.CustomerMapper;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;

public class AdminController {

    private final AdminMapper adminMapper;
    private final CustomerMapper customerMapper;

    // Constructor injection
    public AdminController(AdminMapper adminMapper, CustomerMapper customerMapper) {
        this.adminMapper = adminMapper;
        this.customerMapper = customerMapper;
    }


    public void createAdmin(@NotNull Context ctx) {
        // Henter form parametre, 2 passwords for at tjekke om de er ens
        String email = ctx.formParam("email");
        String password1 = ctx.formParam("password");
        String password2 = ctx.formParam("password2");

        // Validerer emailen ved brug af en boolean som tjekker inputtet fra formen.
        if (!isValidEmail(email)) {
            ctx.attribute("message", "Invalid email format");
            ctx.render("admin/createadmin.html");
            return;
        }

        if (password1.equals(password2)) {
            try {
                adminMapper.createAdmin(email, password1);
                ctx.attribute("message", "Du har oprettet en admin med mailen: " + email);
                ctx.render("admin/controlpanel.html");

            } catch (DatabaseException e) {
                // Hvis brugernavnet allerede findes,så returneres denne besked
                ctx.attribute("message", "Dit brugernavn findes allerede. Prøv igen eller log ind.");
                ctx.render("admin/createadmin.html");
            }
        } else {
            ctx.attribute("message", "Passwords do not match");
            ctx.render("admin/createadmin.html");
        }
    }

    private static boolean isValidEmail(String email) {
        return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    public void adminLogin(@NotNull Context ctx) {
        // Henter form parametre til login
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        // Tjek om admin findes i databasen
        try {
            AdminDTO adminDTO = adminMapper.login(email, password);
            ctx.sessionAttribute("currentAdmin", adminDTO);

            // Hvis Admin findes i DB
            ctx.render("admin/controlpanel.html");

        } catch (DatabaseException | SQLException e) {
            // Hvis admin IKKE findes, send tilbage til login siden med fejl besked
            ctx.attribute("message", e.getMessage());
            ctx.render("admin/adminlogin.html");
        }
    }

    public void showInsertBalancePage(Context ctx) {
        List<CustomerDTO> allCustomers = customerMapper.getAllCustomers(); // You must implement this
        ctx.attribute("allCustomers", allCustomers);
        ctx.render("/admin/addbalance.html");
    }
}

