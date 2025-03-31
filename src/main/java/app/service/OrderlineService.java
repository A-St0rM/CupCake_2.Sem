package app.service;

import app.DTO.CupcakeDTO;
import app.entities.Cupcake;
import app.entities.Order;
import app.entities.Orderline;
import app.exceptions.DatabaseException;
import app.persistence.CupcakeMapper;
import app.persistence.OrderMapper;
import app.persistence.OrderlineMapper;
import app.persistence.StatusMapper;

import java.time.LocalDate;
import java.util.List;

public class OrderlineService {


    private final CupcakeMapper cupcakeMapper;
    private final OrderlineMapper orderlineMapper;
    private final OrderMapper orderMapper;
    private final StatusMapper statusMapper;

    public OrderlineService(CupcakeMapper cupcakeMapper, OrderlineMapper orderlineMapper, OrderMapper orderMapper, StatusMapper statusMapper) {
        this.cupcakeMapper = cupcakeMapper;
        this.orderlineMapper = orderlineMapper;
        this.orderMapper = orderMapper;
        this.statusMapper = statusMapper;
    }

    public void createAndSaveOrderline(int orderId, int customerId) {

        boolean orderExists = orderMapper.doesOrderExist(orderId);

        if (!orderExists) {

            //Create a new status id for order
            int statusId = statusMapper.createStatus();

            Order order = new Order(
                    orderId,
                    customerId,
                    LocalDate.now(),
                    0,
                    statusId
            );
            orderMapper.insertOrder(order);
        }

        //add the orderline
        List<CupcakeDTO> cupcakeList = cupcakeMapper.getAllCupcakesDTO();
        int totalPrice = 0;
        for (CupcakeDTO c : cupcakeList) {
            totalPrice += c.getPrice();
        }

        Orderline orderline = new Orderline(orderId, totalPrice);
        orderlineMapper.insertOrderline(orderline);

        //update the total order price
        orderMapper.updateOrderById(orderId);
    }

    //To make it more clean for our method used for only customerId
    public void createAndSaveOrderline(int customerId) {
        createAndSaveOrderline(0, customerId);
    }

    public void UpdateOrderlinePrice(int orderlineId, Cupcake cupcake) {

        int currentPrice = orderlineMapper.getOrderlinePriceById(orderlineId);

        int newPrice = currentPrice + cupcake.getPrice();

        boolean succes = orderlineMapper.updateOrderlineById(orderlineId, newPrice);

        if (!succes) {
            throw new DatabaseException("Failed to update orderline price");
        }

        int orderId = orderlineMapper.getOrderIdByOderlineId(orderlineId);
        orderMapper.updateOrderById(orderId);
    }

    public void deleteCupcakeAndUpdateOrderline(int orderlineId, int cupcakeId) throws DatabaseException {
        Cupcake cupcake = cupcakeMapper.getCupcakeById(cupcakeId);

        if (cupcake == null) {
            throw new DatabaseException("Cupcake not found, cannot update orderline");
        }

        int currentPrice = orderlineMapper.getOrderlinePriceById(orderlineId);

        int newPrice = currentPrice - cupcake.getPrice();

        boolean cupcakeDeleted = cupcakeMapper.deleteCupcakeById(cupcakeId);

        if (!cupcakeDeleted) {
            throw new DatabaseException("Failed to delete cupcake");
        }

        boolean orderlineUpdated = orderlineMapper.updateOrderlineById(orderlineId, newPrice);

        if (!orderlineUpdated) {
            throw new DatabaseException("Failed to update orderline price");
        }

        int orderId = orderlineMapper.getOrderIdByOderlineId(orderlineId);
        orderMapper.updateOrderById(orderId);
    }

    public Cupcake getCupcakeById(int cupcakeId) throws DatabaseException {
        return cupcakeMapper.getCupcakeById(cupcakeId);
    }

    public List<CupcakeDTO> getCupcakesInCart(int customerId) {
        // 1. Find den nyeste ordre for brugeren
        int orderId = orderMapper.getLatestOrderIdByCustomer(customerId);

        // 2. Find orderline til den ordre
        int orderlineId = orderlineMapper.getOrderlineIdByOrderId(orderId);

        // 3. Hent cupcakes for den orderline
        return cupcakeMapper.getCupcakesByOrderlineId(orderlineId);
    }

    public int getLatestOrderId(int customerId) {
        return orderMapper.getLatestOrderIdByCustomer(customerId);
    }
}
