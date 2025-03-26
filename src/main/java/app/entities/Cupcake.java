package app.entities;

public class Cupcake {
    private int cupcake_id;
    private int cupcake_top_id;
    private int cupcake_bottom_id;
    private double price;

    public Cupcake(int cupcake_id, int cupcake_top_id, int cupcake_bottom_id, double price) {
        this.cupcake_id = cupcake_id;
        this.cupcake_top_id = cupcake_top_id;
        this.cupcake_bottom_id = cupcake_bottom_id;
        this.price = price;

    }

    public int getCupcake_id() {
        return cupcake_id;
    }

    public void setCupcake_id(int cupcake_id) {
        this.cupcake_id = cupcake_id;
    }

    public int getCupcake_top_id() {
        return cupcake_top_id;
    }

    public void setCupcake_top_id(int cupcake_top_id) {
        this.cupcake_top_id = cupcake_top_id;
    }

    public int getCupcake_bottom_id() {
        return cupcake_bottom_id;
    }

    public void setCupcake_bottom_id(int cupcake_bottom_id) {
        this.cupcake_bottom_id = cupcake_bottom_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
