package app.service;

import app.DTO.CupcakeDTO;
import app.entities.Cupcake;
import app.entities.Order;
import app.entities.Orderline;
import app.exceptions.DatabaseException;
import app.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderlineService {


    private final CupcakeMapper cupcakeMapper;
    private final OrderlineMapper orderlineMapper;
    private final OrderMapper orderMapper;
    private final StatusMapper statusMapper;
    private final CustomerMapper customerMapper;

    public OrderlineService(CupcakeMapper cupcakeMapper, OrderlineMapper orderlineMapper, OrderMapper orderMapper, StatusMapper statusMapper, CustomerMapper customerMapper) {
        this.cupcakeMapper = cupcakeMapper;
        this.orderlineMapper = orderlineMapper;
        this.orderMapper = orderMapper;
        this.statusMapper = statusMapper;
        this.customerMapper = customerMapper;
    }

    public void createAndSaveOrderline(int orderId, int customerId) {
        if (orderId == 0 || !orderMapper.doesOrderExist(orderId)) {
            int statusId = statusMapper.createStatus();
            Order order = new Order(0, customerId, LocalDate.now(), 0, statusId);
            orderId = orderMapper.insertOrder(order); // Fanger auto-genereret ID
        }

        // Opret tom orderline med pris = 0. Vi opdatere prisen efter
        Orderline orderline = new Orderline(orderId, 0);
        orderlineMapper.insertOrderline(orderline);
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
        int cupcakeTotalPrice = cupcake.getPrice() * cupcake.getQuantity();

        int newPrice = currentPrice - cupcakeTotalPrice;

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

    public int getOrderlineIdByOrderId(int orderId){
        return orderlineMapper.getOrderlineIdByOrderId(orderId);
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

    public boolean isOrderPaid(int statusId) {
        return statusMapper.getPaymentStatus(statusId); // ny metode i StatusMapper
    }

    public int getStatusIdByOrderId(int orderId) {
        return orderMapper.getStatusIdByOrderId(orderId);
    }

    public BigDecimal getCustomerBalance(int customerId) {
        return customerMapper.getBalanceByCustomerId(customerId);
    }
}
