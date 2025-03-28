package app.controllers;

import app.InjectorHandler.DependencyInjector;
import app.entities.Cupcake;
import app.entities.CupcakeBottom;
import app.entities.CupcakeTop;
import app.persistence.*;
import app.service.CupcakeService;
import io.javalin.Javalin;


public class RoutingController {


    public static void startRouting(Javalin app, ConnectionPool connectionPool) {

        DependencyInjector di = new DependencyInjector(connectionPool);

        CupcakeController cupcakeController = di.getCupcakeController();
        AdminController adminController = di.getAdminController();
        CupcakeBottomController cupcakeBottomController = di.getCupcakeBottomController();
        CupcakeTopController cupcakeTopController = di.getCupcakeTopController();
        CustomerController customerController = di.getCustomerController();


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
        app.post("/deleteCupcake", (ctx) -> cupcakeController.deleteCupcake(ctx));

    }
}
