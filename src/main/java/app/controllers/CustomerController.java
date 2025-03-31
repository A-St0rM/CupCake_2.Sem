package app.controllers;

import app.DTO.CustomerDTO;
import app.entities.Customer;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CustomerMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class CustomerController {

    private final CustomerMapper customerMapper;

    public CustomerController( CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }


    public void createcustomer(@NotNull Context ctx) {
        // Henter form parametre, 2 passwords for at tjekke om de er ens
        String email = ctx.formParam("email");
        String password1 = ctx.formParam("password");
        String password2 = ctx.formParam("password2");

        // Validerer emailen ved brug af en boolean som tjekker inputtet fra formen.
        if (!isValidEmail(email)) {
            ctx.attribute("message", "Invalid email format");
            ctx.render("createcustomer.html");
            return;
        }

        if (password1.equals(password2)) {
            try {
                customerMapper.createCustomer(email, password1);
                ctx.attribute("message", "Du er hermed oprettet som kunde med mailen: " + email);
                ctx.render("index.html");

            } catch (DatabaseException e) {
                // Hvis brugernavnet allerede findes, så returneres denne besked
                ctx.attribute("message", "Dit brugernavn findes allerede. Prøv igen eller log ind.");
                ctx.render("createcustomer.html");
            }
        } else {
            ctx.attribute("message", "Passwords do not match");
            ctx.render("createcustomer.html");}
    }

    // Denne metode validerer emailen, selv om man ikke kan få lov i formen, så er det godt at have java validation også
    private static boolean isValidEmail(String email) {
        return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    public void login(@NotNull Context ctx) {
        // Henter form parametre til login
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        // Tjek om brugeren findes i databasen
        try {
            CustomerDTO customerDTO = customerMapper.login(email, password);
            ctx.sessionAttribute("currentCustomer", customerDTO);

            ctx.sessionAttribute("currentUserId", customerDTO.getCustomerId());

            // Hvis customer findes i DB
            // TODO: Her vil der så sendes en attribut med en liste af alle tidligere ordre
            ctx.redirect("/cupcakeshop");

        } catch (DatabaseException | SQLException e) {

            ctx.attribute("message", "Der opstod en fejl under login. Prøv igen.");
            // Hvis customer IKKE findes, send tilbage til login siden med fejl besked
            System.err.println("Login fejl: " + e.getMessage());
            ctx.render("login.html");
        }
    }
}




