package app.entities;

public class CupcakeOrderline {
    private int cupcake_orderline_id;
    private int cupcake_id;
    private int orderline_id;
    private double cupcake_price;


    public CupcakeOrderline(int cupcake_orderline_id, int cupcake_id, int orderline_id, double cupcake_price) {
        this.cupcake_orderline_id = cupcake_orderline_id;
        this.cupcake_id = cupcake_id;
        this.orderline_id = orderline_id;
        this.cupcake_price = cupcake_price;
    }

    public int getCupcake_orderline_id() {
        return cupcake_orderline_id;
    }

    public void setCupcake_orderline_id(int cupcake_orderline_id) {
        this.cupcake_orderline_id = cupcake_orderline_id;
    }

    public int getCupcake_id() {
        return cupcake_id;
    }

    public void setCupcake_id(int cupcake_id) {
        this.cupcake_id = cupcake_id;
    }

    public int getOrderline_id() {
        return orderline_id;
    }

    public void setOrderline_id(int orderline_id) {
        this.orderline_id = orderline_id;
    }

    public double getCupcake_price() {
        return cupcake_price;
    }

    public void setCupcake_price(double cupcake_price) {
        this.cupcake_price = cupcake_price;
    }
}
