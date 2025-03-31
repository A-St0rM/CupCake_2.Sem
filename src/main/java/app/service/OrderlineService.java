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
        if (orderId == 0 || !orderMapper.doesOrderExist(orderId)) {
            int statusId = statusMapper.createStatus();
            Order order = new Order(0, customerId, LocalDate.now(), 0, statusId);
            orderId = orderMapper.insertOrder(order);
        }

        // 🔁 Her bruger vi kun cupcakes for denne kunde
        List<CupcakeDTO> cupcakeList = getCupcakesInCart(customerId);

        int totalPrice = 0;
        for (CupcakeDTO c : cupcakeList) {
            totalPrice += c.getPrice() * c.getQuantity();
        }

        //TODO: Prisen laves ikke når vi opretter orderline/orders
        Orderline orderline = new Orderline(orderId, totalPrice);
        orderlineMapper.insertOrderline(orderline);
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
