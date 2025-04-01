package app.controllers;

import app.DTO.CupcakeDTO;
import app.entities.Cupcake;
import app.entities.Customer;
import app.entities.Orderline;
import app.exceptions.DatabaseException;
import app.persistence.CustomerMapper;
import app.persistence.OrderlineMapper;
import app.service.OrderlineService;
import io.javalin.http.Context;

import java.math.BigDecimal;
import java.util.List;


public class OrderlineController {

    private final OrderlineService orderlineService;
    private final OrderlineMapper orderlineMapper;
    private final CustomerMapper customerMapper;

    public OrderlineController(OrderlineService orderlineService, OrderlineMapper orderlineMapper, CustomerMapper customerMapper){
        this.orderlineService = orderlineService;
        this.orderlineMapper = orderlineMapper;
        this.customerMapper = customerMapper;
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
            Integer customerId = ctx.sessionAttribute("currentUserId");
            if (customerId == null) {
                ctx.redirect("/login");
                return;
            }

            int cupcakeId = Integer.parseInt(ctx.formParam("cupcakeId"));

            int orderId = orderlineService.getLatestOrderId(customerId);
            int orderlineId = orderlineService.getOrderlineIdByOrderId(orderId);

            orderlineService.deleteCupcakeAndUpdateOrderline(orderlineId, cupcakeId);
            ctx.redirect("/cart");
        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid cupcake ID: " + e.getMessage());
        } catch (DatabaseException e) {
            ctx.status(400).result("Fejl ved sletning: " + e.getMessage());
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

            int orderId = orderlineService.getLatestOrderId(customerId);
            int statusId = orderlineService.getStatusIdByOrderId(orderId);
            boolean isPaid = orderlineService.isOrderPaid(statusId);

            if (isPaid) {
                ctx.attribute("cupcakes", List.of()); // Tom liste
                ctx.attribute("totalPrice", 0);
                ctx.attribute("balance", orderlineService.getCustomerBalance(customerId));
                ctx.render("cart.html");
                return;
            }

            List<CupcakeDTO> cupcakes = orderlineService.getCupcakesInCart(customerId);
            int total = cupcakes.stream().mapToInt(c -> c.getPrice() * c.getQuantity()).sum();

            ctx.attribute("cupcakes", cupcakes);
            ctx.attribute("totalPrice", total);
            ctx.attribute("balance", orderlineService.getCustomerBalance(customerId));
            ctx.render("cart.html");

        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Fejl ved visning af kurv: " + e.getMessage());
        }
    }

}
