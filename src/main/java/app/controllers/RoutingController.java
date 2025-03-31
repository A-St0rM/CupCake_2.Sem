package app.controllers;

import app.InjectorHandler.DependencyInjector;
import app.persistence.*;
import io.javalin.Javalin;



public class RoutingController {


    public static void startRouting(Javalin app, ConnectionPool connectionPool) {

        DependencyInjector di = new DependencyInjector(connectionPool);

        CupcakeController cupcakeController = di.getCupcakeController();
        AdminController adminController = di.getAdminController();
        CupcakeBottomController cupcakeBottomController = di.getCupcakeBottomController();
        CupcakeTopController cupcakeTopController = di.getCupcakeTopController();
        CustomerController customerController = di.getCustomerController();
        OrderlineController orderlineController = di.getOrderlineController();
        StatusController statusController = di.getStatusController();
        OrderController orderController = di.getOrderController();


        // General Routing
        app.get("/", ctx -> ctx.render("index.html"));
        app.get("/logout", ctx -> {
            ctx.sessionAttribute("currentCustomer", null);
            ctx.sessionAttribute("currentAdmin", null);
            ctx.req().getSession().invalidate();
            ctx.redirect("/");
        });


        // Routing for Customer
        app.get("/login", ctx -> ctx.render("login.html"));
        app.post("login", ctx -> customerController.login(ctx));
        app.get("createcustomer", ctx -> ctx.render("createcustomer"));
        app.post("/createcustomer", ctx -> customerController.createcustomer(ctx));

        // Routing for cupcake bottom and top
        app.get("/cupcakebottoms", ctx -> cupcakeBottomController.getAllCupcakeBottoms(ctx));
        app.get("/cupcaketops", ctx -> cupcakeTopController.getAllCupcakeTops(ctx));

        // Routing for Admin
        app.post("admin/login", ctx -> adminController.adminLogin(ctx));
        app.get("createAdmin", ctx -> ctx.render("createAdmin"));
        app.post("createAdmin", ctx -> adminController.createAdmin(ctx));

        //Routing for cupcake
        app.post("/addCupcake", ctx -> cupcakeController.addCupcake(ctx));
        app.post("/deleteCupcake", ctx -> cupcakeController.deleteCupcake(ctx));
        app.put("/updateCupcake", ctx -> cupcakeController.updateCupcake(ctx));
        app.get("/getAllCupcakes", ctx -> cupcakeController.getAllCupcakes(ctx));

        //Routing for orderline
        app.post("/createOrderline", ctx -> orderlineController.createOrderline(ctx));
        app.put("/updateOrderlinePrice", ctx -> orderlineController.updateOrderlinePrice(ctx));
        app.post("/deleteCupcakeFromOrderline", ctx -> orderlineController.deleteCupcakeFromOrderline(ctx));
        app.get("/getAllOrderlines", ctx -> orderlineController.getAllOrderlines(ctx));

        // Routing for Status updates
        app.post("/status/updatePayment", ctx -> statusController.updatePaymentStatus(ctx));
        app.post("/status/updatePickup", ctx -> statusController.updatePickupStatus(ctx));

        //Routing for order
        app.get("/getAllOrders", ctx -> orderController.getAllOrders(ctx));
        app.get("/orders/status", ctx -> orderController.getOrdersWithStatus(ctx));
    }
}
