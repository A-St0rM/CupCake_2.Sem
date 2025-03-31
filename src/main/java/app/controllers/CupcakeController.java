package app.controllers;

import app.DTO.CupcakeDTO;
import app.entities.Cupcake;
import app.entities.CupcakeBottom;
import app.entities.CupcakeTop;
import app.persistence.CupcakeBottomMapper;
import app.persistence.CupcakeMapper;
import app.persistence.CupcakeTopMapper;
import app.service.CupcakeService;
import io.javalin.http.Context;

import java.util.List;


public class CupcakeController {

   private final CupcakeService cupcakeService;
   private final CupcakeMapper cupcakeMapper;
   private final CupcakeBottomMapper cupcakeBottomMapper;
   private final CupcakeTopMapper cupcakeTopMapper;

   public CupcakeController(CupcakeService cupcakeService, CupcakeMapper cupcakeMapper, CupcakeTopMapper cupcakeTopMapper, CupcakeBottomMapper cupcakeBottomMapper){
       this.cupcakeService = cupcakeService;
       this.cupcakeMapper = cupcakeMapper;
       this.cupcakeBottomMapper = cupcakeBottomMapper;
       this.cupcakeTopMapper = cupcakeTopMapper;
   }

    // Handle POST request to add a new cupcake
    public void addCupcake(Context ctx) {
        try {
            int topId = Integer.parseInt(ctx.formParam("topId"));
            int bottomId = Integer.parseInt(ctx.formParam("bottomId"));
            int quantity = Integer.parseInt(ctx.formParam("quantity"));

            cupcakeService.createAndSaveCupcake(topId, bottomId, quantity);
            ctx.redirect("/cupcakeshop");
        } catch (Exception e) {
            ctx.status(400).result("Invalid input: " + e.getMessage());
        }
    }

    public void deleteCupcake(Context ctx){
       try {
           int cupcakeId = Integer.parseInt((ctx.formParam("cupcakeId")));
           boolean isDeleted = cupcakeMapper.deleteCupcakeById(cupcakeId);

           if (isDeleted) {
               System.out.println("successfully deleted");
               ctx.redirect("/homepage"); //TODO: a page for the list
           } else {
               ctx.status(404).result("Cupcake not found or could not be deleted.");
           }

           // The parseInt() method can throw a NumberFormatException if the string does not contain a parsable integer
       } catch (NumberFormatException e) {
            ctx.status(400).result("Invalid cupcake ID: " + e.getMessage());
       } catch (Exception e) {
           ctx.status(500).result("An error occurred while deleting the cupcake");
           e.printStackTrace(); // Log the exception for debugging
       }
    }

    public void updateCupcake(Context ctx){

       try {
           int cupcakeId = Integer.parseInt(ctx.formParam("cupcakeId"));
           int quantity = Integer.parseInt(ctx.formParam("quantity"));

           boolean isUpdated = cupcakeMapper.updateCupcakeById(cupcakeId, quantity);

           if(isUpdated){
               System.out.println("Successfully updated the cupcake");
               ctx.redirect("/homepage"); //TODO: a page for the list
           }
           else {
               ctx.status(404).result("Cupcake not found or could not be updated.");
           }
       } catch (NumberFormatException e) {
           ctx.status(400).result("Invalid cupcakeId : " + e.getMessage());
       }
    }

    public void getAllCupcakes(Context ctx){
       try {
           List<CupcakeDTO> cupcakes = cupcakeMapper.getAllCupcakesDTO();

           if (cupcakes.isEmpty()) {
               ctx.status(400).result("No cupcakes found");
           } else {
               ctx.attribute("cupcakes", cupcakes);
               ctx.render("/homepage.html"); //TODO: a page for the list
           }
       }
       catch (Exception e){
           ctx.status(500).result("An error occurred while fetching cupcakes");
           e.printStackTrace(); //For debugging
       }
    }

    public void showOrderPage(Context ctx) {
        try {
            List<CupcakeTop> tops = cupcakeTopMapper.getAllCupcakeTops();
            List<CupcakeBottom> bottoms = cupcakeBottomMapper.getAllCupcakeBottoms();
            List<CupcakeDTO> cupcakes = cupcakeMapper.getAllCupcakesDTO();

            ctx.attribute("cupcakeTops", tops);
            ctx.attribute("cupcakeBottoms", bottoms);
            ctx.attribute("cupcakes", cupcakes);

            ctx.render("cupcakeshop.html");
        } catch (Exception e) {
            ctx.status(500).result("Error loading order page: " + e.getMessage());
        }
    }
}
