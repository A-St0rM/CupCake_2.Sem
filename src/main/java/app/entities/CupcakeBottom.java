package app.entities;

public class CupcakeBottom {

    private int cupcake_bottom_id;
    private String cupcake_bottom_name;
    private double price;

    public CupcakeBottom(int cupcake_bottom_id,  double price, String cupcake_bottom_name) {
        this.cupcake_bottom_id = cupcake_bottom_id;
        this.cupcake_bottom_name = cupcake_bottom_name;
        this.price = price;
    }
    public CupcakeBottom(double price, String cupcake_bottom_name) {
        this.cupcake_bottom_name = cupcake_bottom_name;
        this.price = price;
    }

    public int getCupcake_bottom_id() {
        return cupcake_bottom_id;
    }

    public void setCupcake_bottom_id(int cupcake_bottom_id) {
        this.cupcake_bottom_id = cupcake_bottom_id;
    }

    public String getCupcake_bottom_name() {
        return cupcake_bottom_name;
    }

    public void setCupcake_bottom_name(String cupcake_bottom_name) {
        this.cupcake_bottom_name = cupcake_bottom_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}


