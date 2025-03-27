package app.controllers;

import app.entities.CupcakeBottom;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeBottomMapper;
import io.javalin.http.Context;
import java.util.List;

public class CupcakeBottomController {

    public static void getAllCupcakeBottoms(Context ctx, ConnectionPool connectionPool) {
        try {
            List<CupcakeBottom> cupcakeBottoms = CupcakeBottomMapper.getAllCupcakeBottoms(connectionPool);
            ctx.attribute("cupcakeBottoms", cupcakeBottoms); // Send data til Thymeleaf
            ctx.render("cupcakebottoms.html"); // Render Thymeleaf-skabelon (TODO: ændrer render)
        } catch (Exception e) {
            ctx.status(500).result("Error fetching cupcake bottoms.");
        }
    }
}

