package app.controllers;

import app.DTO.CupcakeDTO;
import app.entities.Customer;
import app.exceptions.DatabaseException;
import app.persistence.CustomerMapper;
import app.persistence.OrderMapper;
import app.persistence.StatusMapper;
import app.service.OrderlineService;
import io.javalin.http.Context;

import java.math.BigDecimal;
import java.util.List;

public class StatusController {
    private final StatusMapper statusMapper;
    private final OrderMapper orderMapper;
    private final CustomerMapper customerMapper;
    private final OrderlineService orderlineService;

    public StatusController(StatusMapper statusMapper, OrderMapper orderMapper, CustomerMapper customerMapper, OrderlineService orderlineService) {
        this.statusMapper = statusMapper;
        this.orderMapper = orderMapper;
        this.customerMapper = customerMapper;
        this.orderlineService = orderlineService;
    }

    public void updatePaymentStatus(Context ctx) {
        try {
            int orderId = Integer.parseInt(ctx.formParam("orderId"));
            boolean isPaid = Boolean.parseBoolean(ctx.formParam("isPaid"));

            // Update database via StatusMapper
            statusMapper.updatePaymentStatus(orderId, isPaid);
            ctx.redirect("/viewOrders");
        } catch (DatabaseException | NumberFormatException e) {
            ctx.status(500).result("Error updating payment status: " + e.getMessage());
        }
    }

    public void updatePickupStatus(Context ctx) {
        try {
            int orderId = Integer.parseInt(ctx.formParam("orderId"));
            boolean isPickedUp = Boolean.parseBoolean(ctx.formParam("isPickedUp"));

            // Update database via StatusMapper
            statusMapper.updatePickupStatus(orderId, isPickedUp);
            ctx.redirect("/viewOrders");
        } catch (DatabaseException | NumberFormatException e) {
            ctx.status(500).result("Error updating pickup status: " + e.getMessage());
        }
    }

    public void handlePurchase(Context ctx) {
        try {
            Integer customerId = ctx.sessionAttribute("currentUserId");
            if (customerId == null) {
                ctx.redirect("/login");
                return;
            }

            // Hent ordre og pris
            int orderId = orderMapper.getLatestOrderIdByCustomer(customerId);
            int totalPrice = orderMapper.getTotalPriceByOrderId(orderId);
            int statusId = orderMapper.getStatusIdByOrderId(orderId);

            // Hent kundens balance
            BigDecimal balance = customerMapper.getBalanceByCustomerId(customerId);

            // Tjek om kunden har nok penge
            if (balance.compareTo(BigDecimal.valueOf(totalPrice)) < 0) {
                // Tilføj fejlbesked og data og vis kurven igen
                List<CupcakeDTO> cupcakes = orderlineService.getCupcakesInCart(customerId);
                ctx.attribute("cupcakes", cupcakes);
                ctx.attribute("totalPrice", totalPrice);
                ctx.attribute("balance", balance);
                ctx.attribute("error", "Du har ikke nok penge til at gennemføre købet 💸");

                ctx.render("cart.html");
                return;
            }

            // Træk pengene og opdater status
            customerMapper.updateCustomerBalance(customerId, balance.subtract(BigDecimal.valueOf(totalPrice)));
            statusMapper.updatePaymentStatus(statusId, true);

            ctx.redirect("/confirmation");

        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Fejl ved betaling: " + e.getMessage());
        }
    }

}