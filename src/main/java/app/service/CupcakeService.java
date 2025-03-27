package app.service;

import app.entities.Cupcake;
import app.persistence.CupcakeBottomMapper;
import app.persistence.CupcakeMapper;
import app.persistence.CupcakeTopMapper;

public class CupcakeService {

    public void createAndSaveCupcake(int topId, int bottomId) {
        double topPrice = CupcakeTopMapper.getPriceById(topId);
        double bottomPrice = CupcakeBottomMapper.getPriceById(bottomId);
        double totalPrice = topPrice + bottomPrice;

        Cupcake cupcake = new Cupcake(topId, bottomId, totalPrice);
        CupcakeMapper.saveCupcake(cupcake);
    }
}
