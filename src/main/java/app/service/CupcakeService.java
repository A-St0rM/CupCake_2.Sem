package app.service;

import app.entities.Cupcake;
import app.persistence.CupcakeBottomMapper;
import app.persistence.CupcakeMapper;
import app.persistence.CupcakeTopMapper;


public class CupcakeService {

    private CupcakeTopMapper cupcakeTopMapper;
    private CupcakeBottomMapper cupcakeBottomMapper;
    private CupcakeMapper cupcakeMapper;

    public CupcakeService(CupcakeBottomMapper cupcakeBottomMapper, CupcakeTopMapper cupcakeTopMapper, CupcakeMapper cupcakeMapper){
        this.cupcakeBottomMapper = cupcakeBottomMapper;
        this.cupcakeTopMapper = cupcakeTopMapper;
        this.cupcakeMapper = cupcakeMapper;
    }

    public void createAndSaveCupcake(int topId, int bottomId, int quantity) {
        double topPrice = cupcakeTopMapper.getPriceById(topId);
        double bottomPrice = cupcakeBottomMapper.getPriceById(bottomId);
        double totalPrice = topPrice + bottomPrice;

        Cupcake cupcake = new Cupcake(topId, bottomId, totalPrice, quantity);
        cupcakeMapper.saveCupcake(cupcake);
    }
}
