package app.controllers;

import app.DTO.CustomerDTO;
import app.DTO.CustomerOrderDTO;
import app.DTO.PurchaseOverviewDTO;
import app.entities.Cupcake;
import app.entities.Customer;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CustomerMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

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
                ctx.render("login.html");

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

    public void insertNewBalance(Context ctx) {
        try {
            // Hent kunde-id og beløb fra formularen
            String customerIdStr = ctx.formParam("customerId");
            String amountStr = ctx.formParam("amount");

            if (customerIdStr == null || amountStr == null) {
                ctx.status(400).result("Mangler kunde eller beløb");
                return;
            }

            int customerId = Integer.parseInt(customerIdStr);
            BigDecimal amount = new BigDecimal(amountStr);

            // Hent nuværende saldo for den valgte kunde
            BigDecimal currentBalance = customerMapper.getBalanceByCustomerId(customerId);

            // Beregn og opdater saldo
            BigDecimal newBalance = currentBalance.add(amount);
            boolean updated = customerMapper.updateCustomerBalance(customerId, newBalance);

            if (updated) {
                ctx.redirect("/addbalance?success=true");
            } else {
                ctx.status(400).result("Saldo blev ikke opdateret");
            }

        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Fejl ved indsættelse af penge: " + e.getMessage());
        }
    }


    public void getAllOrders(@NotNull Context ctx) {
        try {
            Integer customerId = ctx.sessionAttribute("currentUserId");

            if (customerId == null) {
                ctx.redirect("/login");
                return;
            }

            List<PurchaseOverviewDTO> purchases = customerMapper.getPurchaseOverviewByCustomerId(customerId);
            ctx.attribute("purchases", purchases);
            ctx.render("purchasehistory.html");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke hente købsoversigt: " + e.getMessage());
        }
    }
}




