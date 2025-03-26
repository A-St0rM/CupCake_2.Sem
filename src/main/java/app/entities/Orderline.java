package app.entities;

public class Orderline {

    private int orderline_id;
    private int order_id;
    private int cupcake_orderline_id;
    private int quanity;
    private double initial_price;
    private int status_id;

    public Orderline(int orderline_id, int order_id, int cupcake_orderline_id, int quanity, double initial_price, int status_id) {
        this.orderline_id = orderline_id;
        this.order_id = order_id;
        this.cupcake_orderline_id = cupcake_orderline_id;
        this.quanity = quanity;
        this.initial_price = initial_price;
        this.status_id = status_id;
    }
    public Orderline(int order_id, int cupcake_orderline_id, int quanity, double initial_price, int status_id) {
        this.order_id = order_id;
        this.cupcake_orderline_id = cupcake_orderline_id;
        this.quanity = quanity;
        this.initial_price = initial_price;
        this.status_id = status_id;
    }

    public int getOrderline_id() {
        return orderline_id;
    }

    public void setOrderline_id(int orderline_id) {
        this.orderline_id = orderline_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCupcake_orderline_id() {
        return cupcake_orderline_id;
    }

    public void setCupcake_orderline_id(int cupcake_orderline_id) {
        this.cupcake_orderline_id = cupcake_orderline_id;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public double getInitial_price() {
        return initial_price;
    }

    public void setInitial_price(double initial_price) {
        this.initial_price = initial_price;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }
}
