package app.controllers;

import app.DTO.CupcakeDTO;
import app.entities.Cupcake;
import app.entities.Orderline;
import app.exceptions.DatabaseException;
import app.persistence.OrderlineMapper;
import app.service.OrderlineService;
import io.javalin.http.Context;

import java.util.List;


public class OrderlineController {

    private final OrderlineService orderlineService;
    private final OrderlineMapper orderlineMapper;

    public OrderlineController(OrderlineService orderlineService, OrderlineMapper orderlineMapper){
        this.orderlineService = orderlineService;
        this.orderlineMapper = orderlineMapper;
    }

    public void createOrderline(Context ctx) {
        try {
            int orderId = Integer.parseInt(ctx.formParam("orderId"));
            int customerId = Integer.parseInt(ctx.formParam("customerId"));
            orderlineService.createAndSaveOrderline(orderId, customerId);
            ctx.redirect("/orderlines"); //TODO: a page
        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid orderId: " + e.getMessage());
        }
    }

    public void updateOrderlinePrice(Context ctx) {
        try {
            int orderlineId = Integer.parseInt(ctx.formParam("orderlineId"));
            int cupcakeId = Integer.parseInt(ctx.formParam("cupcakeId"));

            Cupcake cupcake = orderlineService.getCupcakeById(cupcakeId); // Get cupcake from DB

            if (cupcake == null) {
                ctx.status(400).result("Cupcake not found");
                return;
            }

            orderlineService.UpdateOrderlinePrice(orderlineId, cupcake);
            ctx.redirect("/orderlines");

        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid input: " + e.getMessage());
        } catch (DatabaseException e) {
            ctx.status(400).result(e.getMessage());
        }
    }

    public void deleteCupcakeFromOrderline(Context ctx) {
        try {
            int orderlineId = Integer.parseInt(ctx.formParam("orderlineId"));
            int cupcakeId = Integer.parseInt(ctx.formParam("cupcakeId"));

            orderlineService.deleteCupcakeAndUpdateOrderline(orderlineId, cupcakeId);
            ctx.redirect("/orderlines"); //TODO: a page
        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid ID format: " + e.getMessage());
        } catch (DatabaseException e) {
            ctx.status(400).result(e.getMessage());
        }
    }

    public void getAllOrderlines(Context ctx) {
        try {
            List<Orderline> orderlines = orderlineMapper.getAllOrderlines();
            ctx.attribute("orderlines", orderlines); // Pass orderlines to Thymeleaf template
            ctx.render("orderlines.html");
        } catch (DatabaseException e) {
            ctx.status(500).result("Error retrieving orderlines: " + e.getMessage());
        }
    }

    public void showCart(Context ctx) {
        try {
            // Her bruger vi "Integer" og IKKE "int", fordi sessionAttribute kan returnere null.
            // Hvis vi brugte "int", og værdien er null, ville vi få en NullPointerException med det samme.
            // Med "Integer" kan vi tjekke for null først:
            Integer customerId = ctx.sessionAttribute("currentUserId");


            if (customerId == null) {
                ctx.redirect("/login");
                return;
            }

            // Hent alle cupcakes i brugerens kurv (fra databasen)
            List<CupcakeDTO> cupcakes = orderlineService.getCupcakesInCart(customerId);

            // Send cupcakes videre til Thymeleaf-template
            ctx.attribute("cupcakes", cupcakes);
            ctx.render("cart.html");

        } catch (Exception e) {
            e.printStackTrace(); // Print fejl i konsollen
            ctx.status(500).result("Fejl ved visning af kurv: " + e.getMessage());
        }
    }
}
