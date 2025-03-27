package app.InjectorHandler;

import app.controllers.*;
import app.persistence.*;
import app.service.CupcakeService;

public class DependencyInjector {
    private final ConnectionPool connectionPool;

    // Mappers
    private final CupcakeBottomMapper cupcakeBottomMapper;
    private final CupcakeTopMapper cupcakeTopMapper;
    private final CupcakeMapper cupcakeMapper;
    private final CustomerMapper customerMapper;
    private final AdminMapper adminMapper;

    // Services
    private final CupcakeService cupcakeService;

    // Controllers
    private final CupcakeController cupcakeController;
    private final AdminController adminController;
    private final CupcakeBottomController cupcakeBottomController;
    private final CupcakeTopController cupcakeTopController;
    private final CustomerController customerController;

    public DependencyInjector(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;

        // Initialiser Mappers
        this.cupcakeBottomMapper = new CupcakeBottomMapper(connectionPool);
        this.cupcakeTopMapper = new CupcakeTopMapper(connectionPool);
        this.cupcakeMapper = new CupcakeMapper(connectionPool);
        this.customerMapper = new CustomerMapper(connectionPool);
        this.adminMapper = new AdminMapper(connectionPool);

        // Initialiser Services
        this.cupcakeService = new CupcakeService(cupcakeBottomMapper, cupcakeTopMapper, cupcakeMapper);

        // Initialiser Controllers
        this.cupcakeController = new CupcakeController(cupcakeService);
        this.adminController = new AdminController(adminMapper);
        this.cupcakeBottomController = new CupcakeBottomController(cupcakeBottomMapper);
        this.cupcakeTopController = new CupcakeTopController(cupcakeTopMapper);
        this.customerController = new CustomerController(customerMapper);
    }

    // Gettere til at hente controllers
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
}
