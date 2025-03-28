package app.controllers;

import app.persistence.ConnectionPool;
import app.persistence.CupcakeMapper;
import app.service.CupcakeService;
import io.javalin.http.Context;


public class CupcakeController {

   private final CupcakeService cupcakeService;
   private CupcakeMapper cupcakeMapper;

   public CupcakeController(CupcakeService cupcakeService, CupcakeMapper cupcakeMapper){
       this.cupcakeService = cupcakeService;
       this.cupcakeMapper = cupcakeMapper;
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

    public void deleteCupcake(Context ctx){
       int cupcakeId = Integer.parseInt((ctx.formParam("cupcakeId")));

       boolean state = cupcakeMapper.deleteCupcakeById(cupcakeId);

       if(state){
           System.out.println("successfully deleted");
           ctx.redirect("/homepage");
       }
       else{
           System.out.println("Something went wrong"); //TODO: made valid logic
       }
    }

    //TODO: Make all the CRUD controllers
}
