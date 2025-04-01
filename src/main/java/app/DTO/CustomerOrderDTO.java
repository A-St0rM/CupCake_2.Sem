package app.DTO;

import java.time.LocalDate;

public class CustomerOrderDTO {

    private int orderId;
    private String customerEmail;
    private double totalPrice;
    private LocalDate orderDate;
    private boolean isPaid;
    private boolean isPickedUp;

    public CustomerOrderDTO(int orderId, String customerEmail, double totalPrice, LocalDate orderDate, boolean isPaid, boolean isPickedUp) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.isPaid = isPaid;
        this.isPickedUp = isPickedUp;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public boolean isPickedUp() {
        return isPickedUp;
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
