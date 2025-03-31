package app.controllers;

import app.exceptions.DatabaseException;
import app.persistence.StatusMapper;
import io.javalin.http.Context;

public class StatusController {
    private final StatusMapper statusMapper;

    public StatusController(StatusMapper statusMapper) {
        this.statusMapper = statusMapper;
    }

    public void updatePaymentStatus(Context ctx) {
        try {
            int statusId = Integer.parseInt(ctx.formParam("statusId"));
            boolean isPaid = Boolean.parseBoolean(ctx.formParam("isPaid"));

            boolean updated = statusMapper.updatePaymentStatus(statusId, isPaid);
            if (updated) {
                ctx.status(200).result("Payment status updated successfully.");
            } else {
                ctx.status(400).result("Failed to update payment status.");
            }
        } catch (DatabaseException | NumberFormatException e) {
            ctx.status(500).result("Error updating payment status: " + e.getMessage());
        }
    }

    public void updatePickupStatus(Context ctx) {
        try {
            int statusId = Integer.parseInt(ctx.formParam("statusId"));
            boolean isPickedUp = Boolean.parseBoolean(ctx.formParam("isPickedUp"));

            boolean updated = statusMapper.updatePickupStatus(statusId, isPickedUp);
            if (updated) {
                ctx.status(200).result("Pickup status updated successfully.");
            } else {
                ctx.status(400).result("Failed to update pickup status.");
            }
        } catch (DatabaseException | NumberFormatException e) {
            ctx.status(500).result("Error updating pickup status: " + e.getMessage());
        }
    }
}