package app.controllers;

import app.entities.CupcakeBottom;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeBottomMapper;
import io.javalin.http.Context;
import java.util.List;

public class CupcakeBottomController {


    private final ConnectionPool connectionPool;

    public CupcakeBottomController(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void getAllCupcakeBottoms(Context ctx) {
        try {
            List<CupcakeBottom> cupcakeBottoms = CupcakeBottomMapper.getAllCupcakeBottoms(connectionPool);
            ctx.attribute("cupcakeBottoms", cupcakeBottoms); // Send data til Thymeleaf
            ctx.render("cupcakebottoms.html"); // Render Thymeleaf-skabelon (TODO: ændrer render)
        } catch (Exception e) {
            ctx.status(500).result("Error fetching cupcake bottoms.");
        }
    }
}

