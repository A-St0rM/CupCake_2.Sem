package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;


public class RoutingController {


    public static void startRouting(Javalin app, ConnectionPool connectionPool) {


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
        app.post("login", ctx -> CustomerController.login(ctx, connectionPool));
        app.get("createcustomer", ctx -> ctx.render("createcustomer"));
        app.post("/createcustomer", ctx -> CustomerController.createcustomer(ctx, connectionPool));

        // Routing for cupcake bottom and top
        app.get("/cupcakebottoms", ctx -> CupcakeBottomController.getAllCupcakeBottoms(ctx, connectionPool));
        app.get("/cupcaketops", ctx -> CupcakeTopController.getAllCupcakeTops(ctx, connectionPool));

        // Routing for Admin
        app.post("adminlogin", ctx -> AdminController.adminLogin(ctx, connectionPool));
        app.get("createAdmin", ctx -> ctx.render("createAdmin"));
        app.post("createAdmin", ctx -> AdminController.createAdmin(ctx, connectionPool));


    }
}
