package app.entities;

public class Orderline {

    private int orderlineId;
    private int orderId;
    private double initialPrice;

    public Orderline(int orderlineId, int orderId, double initialPrice) {
        this.orderlineId = orderlineId;
        this.orderId = orderId;
        this.initialPrice = initialPrice;
    }
    public Orderline(int orderId, double initialPrice) {
        this.orderId = orderId;
        this.initialPrice = initialPrice;
    }

    public int getOrderlineId() {
        return orderlineId;
    }

    public void setOrderlineId(int orderlineId) {
        this.orderlineId = orderlineId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

}
