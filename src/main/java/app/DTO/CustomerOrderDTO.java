package app.DTO;

import java.time.LocalDate;

public class CustomerOrderDTO {

    private int orderId;
    private String customerEmail;
    private double totalPrice;
    private LocalDate orderDate;

    public CustomerOrderDTO(int orderId, String customerEmail, double totalPrice, LocalDate orderDate) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
}
