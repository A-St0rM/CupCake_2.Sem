package app.controllers;

import app.DTO.CustomerOrderDTO;
import app.DTO.OrderStatusDTO;
import app.exceptions.DatabaseException;
import app.persistence.OrderMapper;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderController {

    private final OrderMapper orderMapper;

    public OrderController(OrderMapper orderMapper){
        this.orderMapper = orderMapper;
    }

    public void getAllOrders(@NotNull Context ctx) {
        try {
            List<CustomerOrderDTO> orders = orderMapper.getOrdersWithCustomerInfo();

            ctx.attribute("orders", orders);
            ctx.render("admin/vieworders.html");
        } catch (DatabaseException e) {
            System.out.println("ERROR: " + e.getMessage()); // Log the error
            ctx.attribute("message", "Kunne ikke hente ordrer");
            ctx.render("error.html");
        }
    }

    public void getOrdersWithStatus(Context ctx) {
        try {
            List<OrderStatusDTO> orders = orderMapper.getOrdersWithStatus();
            ctx.attribute("ordersWithStatus", orders);
            ctx.render("orders.html");
        } catch (DatabaseException e) {
            ctx.status(500).result("Error fetching orders with status: " + e.getMessage());
        }
    }

    public void deleteOrder(Context ctx) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.pathParam("id"));
        orderMapper.deleteOrderById(orderId);
        ctx.redirect("/viewOrders");
    }
}
