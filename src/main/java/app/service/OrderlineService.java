package app.service;

import app.entities.Cupcake;
import app.entities.Orderline;
import app.exceptions.DatabaseException;
import app.persistence.CupcakeMapper;
import app.persistence.OrderlineMapper;

import java.util.List;

public class OrderlineService {


    private CupcakeMapper cupcakeMapper;
    private OrderlineMapper orderlineMapper;

    public OrderlineService(CupcakeMapper cupcakeMapper, OrderlineMapper orderlineMapper){
        this.cupcakeMapper = cupcakeMapper;
        this.orderlineMapper = orderlineMapper;
    }

    public void createAndSaveOrderline(int orderId) {

        List<Cupcake> cupcakeList = cupcakeMapper.getAllCupcakes();

        double totalPrice = 0;

        for(Cupcake c : cupcakeList){
            totalPrice += c.getPrice();
        }

        Orderline orderline = new Orderline(orderId, totalPrice);
        orderlineMapper.insertOrderline(orderline);

        //TODO: When creating an orderline, same should orders with the current orderline ID(Adding logic for it when making order mappers and controllers)
    }


    public void UpdateOrderlinePrice(int orderlineId, Cupcake cupcake) {

        double currentPrice = orderlineMapper.getOrderlinePriceById(orderlineId);

        double newPrice = currentPrice + cupcake.getPrice();

        boolean succes = orderlineMapper.updateOrderlineById(orderlineId, newPrice);

       if(!succes){
           throw new DatabaseException("Failed to update orderline price");
       }
    }

    public void deleteCupcakeAndUpdateOrderline(int orderlineId, int cupcakeId) throws DatabaseException{
        Cupcake cupcake = cupcakeMapper.getCupcakeById(cupcakeId);

        if(cupcake == null){
            throw new DatabaseException("Cupcake not found, cannot update orderline");
        }

        double currentPrice = orderlineMapper.getOrderlinePriceById(orderlineId);

        double newPrice = currentPrice - cupcake.getPrice();

        boolean cupcakeDeleted = cupcakeMapper.deleteCupcakeById(cupcakeId);

        if(!cupcakeDeleted){
            throw new DatabaseException("Failed to delete cupcake");
        }

        boolean orderlineUpdated = orderlineMapper.updateOrderlineById(orderlineId, newPrice);

        if(!orderlineUpdated){
            throw new DatabaseException("Failed to update orderline price");
        }
    }
}
