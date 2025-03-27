package app.controllers;

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

    public static void logout(@NotNull Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }

    public static void createAdmin(@NotNull Context ctx, ConnectionPool connectionPool) {
        // Henter form parametre, 2 passwords for at tjekke om de er ens
        String email = ctx.pathParam("email");
        String password1 = ctx.pathParam("password1");
        String password2 = ctx.formParam("password2");

        if (password1.equals(password2)) {
            try {
                AdminMapper.createAdmin(email, password1, connectionPool);
                ctx.attribute("message", "Du er hermed oprettet som admin med mailen: " + email);
                ctx.render("adminIndex.html");

            } catch (DatabaseException e) {
                // Hvis brugernavnet allerede findes,så returneres denne besked
                ctx.attribute("message", "Dit brugernavn findes allerede. Prøv igen eller log ind.");
                ctx.render("createcustomer.html");
            }
        } else {
            ctx.attribute("message", "Passwords do not match");
            ctx.render("createcustomer.html");}
    }

    public static void adminLogin(@NotNull Context ctx, ConnectionPool connectionPool) {
        // Henter form parametre til login
        String email = ctx.queryParam("email");
        String password = ctx.queryParam("password");

        // Tjek om brugeren findes i databasen
        try {
            Admin admin = AdminMapper.login(email, password, connectionPool);

            // Hvis customer findes i DB
            // TODO: Her vil der så sendes en attribut med en liste af alle tidligere ordre
            ctx.render("cupcakeshop.html");

        } catch (DatabaseException | SQLException e) {
            // Hvis customer IKKE findes, send tilbage til login siden med fejl besked
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }
    }
}

