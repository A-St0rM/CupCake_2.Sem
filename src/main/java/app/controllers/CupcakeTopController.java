package app.controllers;

import app.entities.CupcakeTop;
import app.persistence.CupcakeTopMapper;
import io.javalin.http.Context;

import java.util.List;

public class CupcakeTopController {

    private CupcakeTopMapper cupcakeTopMapper;

    public CupcakeTopController(CupcakeTopMapper cupcakeTopMapper) {
        this.cupcakeTopMapper = cupcakeTopMapper;
    }

    public void getAllCupcakeTops(Context ctx) {
        try {
            List<CupcakeTop> cupcakeTops = cupcakeTopMapper.getAllCupcakeTops();
            ctx.attribute("cupcakeTops", cupcakeTops); // Send data til Thymeleaf
            ctx.render("cupcaketops.html"); // Render Thymeleaf-skabelon (TODO: ændrer render)
        } catch (Exception e) {
            ctx.status(500).result("Error fetching cupcake bottoms.");
        }
    }
}
