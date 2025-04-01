package app.InjectorHandler;

import app.controllers.*;
import app.entities.Orderline;
import app.entities.Status;
import app.persistence.*;
import app.service.CupcakeService;
import app.service.OrderlineService;

public class DependencyInjector {
    private final ConnectionPool connectionPool;

    // Mappers
    private final CupcakeBottomMapper cupcakeBottomMapper;
    private final CupcakeTopMapper cupcakeTopMapper;
    private final CupcakeMapper cupcakeMapper;
    private final CustomerMapper customerMapper;
    private final AdminMapper adminMapper;
    private final OrderlineMapper orderlineMapper;
    private final OrderMapper orderMapper;
    private final StatusMapper statusMapper;

    // Services
    private final CupcakeService cupcakeService;
    private final OrderlineService orderlineService;

    // Controllers
    private final CupcakeController cupcakeController;
    private final AdminController adminController;
    private final CupcakeBottomController cupcakeBottomController;
    private final CupcakeTopController cupcakeTopController;
    private final CustomerController customerController;
    private final OrderlineController orderlineController;
    private final StatusController statusController;
    private final OrderController orderController;

    public DependencyInjector(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;

        // Initialiser alle klasser
        this.cupcakeBottomMapper = new CupcakeBottomMapper(connectionPool);
        this.cupcakeTopMapper = new CupcakeTopMapper(connectionPool);
        this.cupcakeMapper = new CupcakeMapper(connectionPool);
        this.customerMapper = new CustomerMapper(connectionPool);
        this.adminMapper = new AdminMapper(connectionPool);
        this.orderlineMapper = new OrderlineMapper(connectionPool);
        this.orderMapper = new OrderMapper(connectionPool);
        this.statusMapper = new StatusMapper(connectionPool);


        this.cupcakeService = new CupcakeService(cupcakeBottomMapper, cupcakeTopMapper, cupcakeMapper, orderlineMapper, orderMapper, statusMapper);
        this.orderlineService = new OrderlineService(cupcakeMapper, orderlineMapper, orderMapper, statusMapper, customerMapper);
        this.cupcakeController = new CupcakeController(cupcakeService, cupcakeMapper, cupcakeTopMapper, cupcakeBottomMapper, orderlineService);
        this.adminController = new AdminController(adminMapper);
        this.cupcakeBottomController = new CupcakeBottomController(cupcakeBottomMapper);
        this.cupcakeTopController = new CupcakeTopController(cupcakeTopMapper);
        this.customerController = new CustomerController(customerMapper);
        this.orderlineController = new OrderlineController(orderlineService, orderlineMapper, customerMapper);
        this.statusController = new StatusController(statusMapper, orderMapper, customerMapper, orderlineService);
        this.orderController = new OrderController(orderMapper);
    }

    public CupcakeController getCupcakeController() {
        return cupcakeController;
    }

    public AdminController getAdminController() {
        return adminController;
    }

    public CupcakeBottomController getCupcakeBottomController() {
        return cupcakeBottomController;
    }

    public CupcakeTopController getCupcakeTopController() {
        return cupcakeTopController;
    }

    public CustomerController getCustomerController() {
        return customerController;
    }

    public OrderlineController getOrderlineController(){
        return orderlineController;
    }

    public StatusController getStatusController() {
        return statusController;
    }

    public OrderController getOrderController() {
        return orderController;
    }
}
