package app.controllers;

import app.entities.Cupcake;
import app.entities.Orderline;
import app.exceptions.DatabaseException;
import app.persistence.OrderlineMapper;
import app.service.OrderlineService;
import io.javalin.http.Context;

import java.util.List;


public class OrderlineController {

    private OrderlineService orderlineService;
    private OrderlineMapper orderlineMapper;

    public OrderlineController(OrderlineService orderlineService, OrderlineMapper orderlineMapper){
        this.orderlineService = orderlineService;
        this.orderlineMapper = orderlineMapper;
    }

    public void createOrderline(Context ctx) {
        try {
            int orderId = Integer.parseInt(ctx.formParam("orderId"));
            orderlineService.createAndSaveOrderline(orderId);
            ctx.redirect("/orderlines"); //TODO: a page
        } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid orderId: " + e.getMessage());
        }
    }

    public void updateOrderlinePrice(Context ctx) {
        try {
            int orderlineId = Integer.parseInt(ctx.pathParam("id"));
            double newPrice = Double.parseDouble(ctx.formParam("price"));

            orderlineService.UpdateOrderlinePrice(orderlineId, new Cupcake(0, 0, 0, newPrice, 1));
            ctx.redirect("/orderlines"); //TODO: a page
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


}
