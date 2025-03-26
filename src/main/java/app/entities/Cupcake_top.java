package app.entities;

public class Cupcake_top {

    private int customer_id;
    private int cupcake_top_id;
    private String cupcake_top_name;
    private double price;

    public Cupcake_top(int customer_id, int cupcake_top_id, String cupcake_top_name, double price) {
        this.customer_id = customer_id;
        this.cupcake_top_id = cupcake_top_id;
        this.cupcake_top_name = cupcake_top_name;
        this.price = price;
    }
    public Cupcake_top(int cupcake_top_id, String cupcake_top_name, double price) {
        this.cupcake_top_id = cupcake_top_id;
        this.cupcake_top_name = cupcake_top_name;
        this.price = price;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getCupcake_top_id() {
        return cupcake_top_id;
    }

    public void setCupcake_top_id(int cupcake_top_id) {
        this.cupcake_top_id = cupcake_top_id;
    }

    public String getCupcake_top_name() {
        return cupcake_top_name;
    }

    public void setCupcake_top_name(String cupcake_top_name) {
        this.cupcake_top_name = cupcake_top_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
