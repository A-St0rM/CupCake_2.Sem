package app.entities;

public class Cupcake {
    private int cupcake_id;
    private int cupcakeTopId;
    private int cupcakeBottomId;
    private double price;
    private int quantity;

    public Cupcake(int cupcake_id, int cupcakeTopId, int cupcakeBottomId, double price, int quantity) {
        this.cupcake_id = cupcake_id;
        this.cupcakeTopId = cupcakeTopId;
        this.cupcakeBottomId = cupcakeBottomId;
        this.price = price;
        this.quantity = quantity;
    }
    public Cupcake(int cupcakeTopId, int cupcakeBottomId, double price, int quantity) {
        this.cupcakeTopId = cupcakeTopId;
        this.cupcakeBottomId = cupcakeBottomId;
        this.price = price;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
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
