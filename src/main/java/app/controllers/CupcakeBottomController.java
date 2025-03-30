package app.controllers;

import app.entities.CupcakeBottom;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeBottomMapper;
import io.javalin.http.Context;
import java.util.List;

public class CupcakeBottomController {

    private final CupcakeBottomMapper cupcakeBottomMapper;

    public CupcakeBottomController(CupcakeBottomMapper cupcakeBottomMapper) {
        this.cupcakeBottomMapper = cupcakeBottomMapper;
    }

    public void getAllCupcakeBottoms(Context ctx) {
        try {
            List<CupcakeBottom> cupcakeBottoms = cupcakeBottomMapper.getAllCupcakeBottoms();
            ctx.attribute("cupcakeBottoms", cupcakeBottoms); // Send data til Thymeleaf
            ctx.render("cupcakebottoms.html"); // Render Thymeleaf-skabelon (TODO: ændrer render)
        } catch (Exception e) {
            ctx.status(500).result("Error fetching cupcake bottoms.");
        }
    }
}

