package app.controllers;

import app.persistence.ConnectionPool;
import app.service.CupcakeService;
import io.javalin.http.Context;


public class CupcakeController {

   private final CupcakeService cupcakeService;

   public CupcakeController(CupcakeService cupcakeService){
       this.cupcakeService = cupcakeService;
   }

    // Handle POST request to add a new cupcake
    public void addCupcake(Context ctx) {
        try {
            int topId = Integer.parseInt(ctx.formParam("topId"));
            int bottomId = Integer.parseInt(ctx.formParam("bottomId"));
            int quantity = Integer.parseInt(ctx.formParam("quantity"));

            cupcakeService.createAndSaveCupcake(topId, bottomId, quantity);
            ctx.redirect("/cupcakes"); // Redirects after adding cupcake
        } catch (Exception e) {
            ctx.status(400).result("Invalid input: " + e.getMessage());
        }
    }
}
