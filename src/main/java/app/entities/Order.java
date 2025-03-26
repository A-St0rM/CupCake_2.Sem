package app.entities;

public class Order {
    private int order_id;
    private int customer_id;
    private int order_date;
    private double total_price;
    private int status_id;

    public Order(int order_id, int customer_id, int order_date, double total_price, int status_id) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.order_date = order_date;
        this.total_price = total_price;
        this.status_id = status_id;
    }
    public Order(int customer_id, int order_date, double total_price, int status_id) {
        this.customer_id = customer_id;
        this.order_date = order_date;
        this.total_price = total_price;
        this.status_id = status_id;
    }


    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getOrder_date() {
        return order_date;
    }

    public void setOrder_date(int order_date) {
        this.order_date = order_date;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }
}
