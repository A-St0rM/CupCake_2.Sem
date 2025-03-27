package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;


public class RoutingController {


    public static void startRouting(Javalin app, ConnectionPool connectionPool) {

        app.get("/", ctx -> ctx.render("index.html"));

        // Routing for Customer
        app.post("login", ctx -> CustomerController.login(ctx, connectionPool));
        app.get("logout", ctx -> CustomerController.logout(ctx));
        app.get("createcustomer", ctx -> ctx.render("createcustomer"));
        app.post("createcustomer", ctx -> CustomerController.createcustomer(ctx, connectionPool));

        // Routing for Admin
        app.post("adminlogin", ctx -> AdminController.adminLogin(ctx, connectionPool));
        app.get("logout", ctx -> AdminController.logout(ctx));
        app.get("createAdmin", ctx -> ctx.render("createAdmin"));
        app.post("createAdmin", ctx -> AdminController.createAdmin(ctx, connectionPool));

    }
}
