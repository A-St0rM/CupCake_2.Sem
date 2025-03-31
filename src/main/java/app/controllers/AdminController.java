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

public class AdminController {

    private final AdminMapper adminMapper;

    // Constructor injection
    public AdminController(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }


    public void createAdmin(@NotNull Context ctx) {
        // Henter form parametre, 2 passwords for at tjekke om de er ens
        String email = ctx.formParam("email");
        String password1 = ctx.formParam("password");
        String password2 = ctx.formParam("password2");

        if (password1.equals(password2)) {
            try {
                adminMapper.createAdmin(email, password1);
                ctx.attribute("adminmessage", "Du har oprettet en admin med mailen: " + email);
                ctx.render("admin/controlpanel.html");

            } catch (DatabaseException e) {
                // Hvis brugernavnet allerede findes,så returneres denne besked
                ctx.attribute("adminmessage", "Dit brugernavn findes allerede. Prøv igen eller log ind.");
                ctx.render("admin/createadmin.html");
            }
        } else {
            ctx.attribute("adminmessage", "Passwords do not match");
            ctx.render("admin/createadmin.html");
        }
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
            ctx.attribute("adminmessage", e.getMessage());
            ctx.render("admin/adminlogin.html");
        }
    }
}

