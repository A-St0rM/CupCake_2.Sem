package app.controllers;

import app.entities.CupcakeTop;
import app.persistence.ConnectionPool;

import app.persistence.CupcakeTopMapper;
import io.javalin.http.Context;

import java.util.List;

public class CupcakeTopController {

    public static void getAllCupcakeTops(Context ctx, ConnectionPool connectionPool) {
        try {
            List<CupcakeTop> cupcakeTops = CupcakeTopMapper.getAllCupcakeTops(connectionPool);
            ctx.attribute("cupcakeTops", cupcakeTops); // Send data til Thymeleaf
            ctx.render("cupcaketops.html"); // Render Thymeleaf-skabelon (TODO: ændrer render)
        } catch (Exception e) {
            ctx.status(500).result("Error fetching cupcake bottoms.");
        }
    }
}
