package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;


public class RoutingController {



    public static void startRouting(Javalin app, ConnectionPool connectionPool){

        app.get("/", ctx ->  ctx.render("index.html"));



    }

}
