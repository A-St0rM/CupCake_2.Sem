package app.controllers;

import app.entities.Customer;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CustomerMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class CustomerController {


    public static void createcustomer(@NotNull Context ctx, ConnectionPool connectionPool) {
        // Henter form parametre, 2 passwords for at tjekke om de er ens
        String email = ctx.formParam("email");
        String password1 = ctx.formParam("password");
        String password2 = ctx.formParam("password2");

        if (password1.equals(password2)) {
            try {
                CustomerMapper.createCustomer(email, password1, connectionPool);
                ctx.attribute("message", "Du er hermed oprettet som kunde med mailen: " + email);
                ctx.render("index.html");

            } catch (DatabaseException e) {
                // Hvis brugernavnet allerede findes,så returneres denne besked
                ctx.attribute("message", "Dit brugernavn findes allerede. Prøv igen eller log ind.");
                ctx.render("createcustomer.html");
        }
    } else {
        ctx.attribute("message", "Passwords do not match");
        ctx.render("createcustomer.html");}
    }

    public static void login(@NotNull Context ctx, ConnectionPool connectionPool) {
        // Henter form parametre til login
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        // Tjek om brugeren findes i databasen
        try {
            Customer customer = CustomerMapper.login(email, password, connectionPool);
            ctx.sessionAttribute("currentCustomer", customer);


            // Hvis customer findes i DB
            // TODO: Her vil der så sendes en attribut med en liste af alle tidligere ordre
            ctx.render("cupcakeshop.html");

        } catch (DatabaseException | SQLException e) {
            // Hvis customer IKKE findes, send tilbage til login siden med fejl besked
            ctx.attribute("message", e.getMessage());
            ctx.render("login.html");
        }
    }
}




