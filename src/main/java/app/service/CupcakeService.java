package app.service;

import app.DTO.CupcakeDTO;
import app.entities.Cupcake;
import app.entities.Order;
import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.*;

import java.time.LocalDate;
import java.util.List;


public class CupcakeService {

    private final CupcakeTopMapper cupcakeTopMapper;
    private final CupcakeBottomMapper cupcakeBottomMapper;
    private final CupcakeMapper cupcakeMapper;
    private final OrderMapper orderMapper;
    private final OrderlineMapper orderlineMapper;
    private final StatusMapper statusMapper;

    public CupcakeService(CupcakeBottomMapper cupcakeBottomMapper, CupcakeTopMapper cupcakeTopMapper, CupcakeMapper cupcakeMapper, OrderlineMapper orderlineMapper, OrderMapper orderMapper, StatusMapper statusMapper){
        this.cupcakeBottomMapper = cupcakeBottomMapper;
        this.cupcakeTopMapper = cupcakeTopMapper;
        this.cupcakeMapper = cupcakeMapper;
        this.orderlineMapper = orderlineMapper;
        this.orderMapper = orderMapper;
        this.statusMapper = statusMapper;
    }

    public void createAndSaveCupcake(int topId, int bottomId, int quantity, int customerId) {
        //Beregner prisen til den enkelte cupcake
        int topPrice = cupcakeTopMapper.getPriceById(topId);
        int bottomPrice = cupcakeBottomMapper.getPriceById(bottomId);
        int totalPrice = topPrice + bottomPrice;

        int orderId;
        int orderlineId;

        //Tjekker om kunden har en orderline som de tidligere var igang med
        try {
            orderId = orderMapper.getLatestOrderIdByCustomer(customerId);
            boolean isPaid = statusMapper.isPaidStatusByOrderId(orderId);

            // Hvis betalt. Opret ny order exception
            if (isPaid) {
                throw new DatabaseException("Latest order is already paid, create new order");
            }

            orderlineId = orderlineMapper.getOrderlineIdByOrderId(orderId);

        } catch (DatabaseException e) {
            int statusId = statusMapper.createStatus();
            Order order = new Order(0, customerId, LocalDate.now(), 0, statusId);
            orderId = orderMapper.insertOrder(order);

            Orderline orderline = new Orderline(orderId, 0);
            orderlineId = orderlineMapper.insertOrderline(orderline);
        }

        Cupcake cupcake = new Cupcake(topId, bottomId, totalPrice, quantity);
        int cupcakeId = cupcakeMapper.insertCupcakeAndReturnId(cupcake);
        orderlineMapper.insertCupcakeToOrderline(cupcakeId, orderlineId);

        // Opdater orderline og order med korrekt totalpris.
        List<CupcakeDTO> cupcakes = cupcakeMapper.getCupcakesByOrderlineId(orderlineId);
        int newOrderlinePrice = 0;
        for (CupcakeDTO c : cupcakes) {
            newOrderlinePrice += c.getPrice() * c.getQuantity();
        }

        orderlineMapper.updateOrderlineById(orderlineId, newOrderlinePrice);
        orderMapper.updateOrderById(orderId);
    }

}
