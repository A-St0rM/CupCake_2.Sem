package app.entities;

public class CupcakeTop {

    private int cupcake_top_id;
    private String cupcake_top_name;
    private double price;

    public CupcakeTop(double price, String cupcake_top_name) {
        this.cupcake_top_name = cupcake_top_name;
        this.price = price;
    }

    public CupcakeTop(int cupcake_top_id, double price, String cupcake_top_name) {
        this.cupcake_top_id = cupcake_top_id;
        this.cupcake_top_name = cupcake_top_name;
        this.price = price;
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


    @Override
    public String toString() {
        return "CupcakeTop{" +
                "cupcake_top_id=" + cupcake_top_id +
                ", cupcake_top_name='" + cupcake_top_name + '\'' +
                ", price=" + price +
                '}';
    }
}
