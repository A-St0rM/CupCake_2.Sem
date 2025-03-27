package app.controllers;

import app.persistence.ConnectionPool;
import app.service.CupcakeService;
import io.javalin.Javalin;


public class RoutingController {


    public static void startRouting(Javalin app, ConnectionPool connectionPool) {
        CupcakeService cupcakeService = new CupcakeService();
        CupcakeController cupcakeController = new CupcakeController(cupcakeService, connectionPool);
        AdminController adminController = new AdminController(connectionPool);
        CupcakeBottomController cupcakeBottomController = new CupcakeBottomController(connectionPool);
        CupcakeTopController cupcakeTopController = new CupcakeTopController(connectionPool);
        CustomerController customerController = new CustomerController(connectionPool);

        app.get("/", ctx -> ctx.render("index.html"));

        // Routing for Customer
        app.get("/login", ctx -> ctx.render("login.html"));
        app.post("login", ctx -> customerController.login(ctx));
        app.get("logout", ctx -> customerController.logout(ctx));
        app.get("createcustomer", ctx -> ctx.render("createcustomer"));
        app.post("createcustomer", ctx -> customerController.createcustomer(ctx));

        // Routing for cupcake bottom and top
        app.get("/cupcakebottoms", ctx -> cupcakeBottomController.getAllCupcakeBottoms(ctx));
        app.get("/cupcaketops", ctx -> cupcakeTopController.getAllCupcakeTops(ctx));

        // Routing for Admin
        app.post("admin/login", ctx -> adminController.adminLogin(ctx));
        app.get("admin/logout", ctx -> adminController.logout(ctx));
        app.get("createAdmin", ctx -> ctx.render("createAdmin"));
        app.post("createAdmin", ctx -> adminController.createAdmin(ctx));

        //Routing for cupcake
        app.post("/addCupcake", (ctx) -> cupcakeController.addCupcake(ctx));


    }
}
