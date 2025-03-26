package app.entities;

public class Cupcake_bottom {

    private int customer_id;
    private int cupcake_bottom_id;
    private String cupcake_bottom_name;
    private double price;

    public Cupcake_bottom(int customer_id, int cupcake_bottom_id, String cupcake_bottom_name, double price) {
        this.customer_id = cupcake_bottom_id;
        this.cupcake_bottom_id = cupcake_bottom_id;
        this.cupcake_bottom_name = cupcake_bottom_name;
        this.price = price;
    }
    public Cupcake_bottom(int cupcake_bottom_id, String cupcake_bottom_name, double price) {
        this.cupcake_bottom_id = cupcake_bottom_id;
        this.cupcake_bottom_name = cupcake_bottom_name;
        this.price = price;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
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


