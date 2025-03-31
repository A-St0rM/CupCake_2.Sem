package app.entities;

public class Orderline {

    private int orderlineId;
    private int orderId;
    private int initialPrice;

    public Orderline(int orderlineId, int orderId, int initialPrice) {
        this.orderlineId = orderlineId;
        this.orderId = orderId;
        this.initialPrice = initialPrice;
    }
    public Orderline(int orderId, int initialPrice) {
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

    public int getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(int initialPrice) {
        this.initialPrice = initialPrice;
    }

}
