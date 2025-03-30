package app.controllers;

import app.DTO.CustomerOrderDTO;
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
            ctx.render("orders.html"); // sends the list to thymeleaf
        } catch (DatabaseException e) {
            ctx.attribute("message", "Kunne ikke hente ordrer");
            ctx.render("error.html"); //TODO: add page
        }
    }


    //TODO: change status on each order (Maybe in status controller/mapper)


}
