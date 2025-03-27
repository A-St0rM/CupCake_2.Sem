package app.entities;

public class Cupcake {
    private int cupcake_id;
    private int cupcakeTopId;
    private int cupcakeBottomId;
    private double price;

    public Cupcake(int cupcake_id, int cupcakeTopId, int cupcakeBottomId, double price) {
        this.cupcake_id = cupcake_id;
        this.cupcakeTopId = cupcakeTopId;
        this.cupcakeBottomId = cupcakeBottomId;
        this.price = price;
    }
    public Cupcake(int cupcakeTopId, int cupcakeBottomId, double price) {
        this.cupcakeTopId = cupcakeTopId;
        this.cupcakeBottomId = cupcakeBottomId;
        this.price = price;
    }

    public int getCupcake_id() {
        return cupcake_id;
    }

    public void setCupcake_id(int cupcake_id) {
        this.cupcake_id = cupcake_id;
    }

    public int getCupcakeTopId() {
        return cupcakeTopId;
    }

    public void setCupcakeTopId(int cupcakeTopId) {
        this.cupcakeTopId = cupcakeTopId;
    }

    public int getCupcakeBottomId() {
        return cupcakeBottomId;
    }

    public void setCupcakeBottomId(int cupcakeBottomId) {
        this.cupcakeBottomId = cupcakeBottomId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
