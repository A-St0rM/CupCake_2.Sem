package app.DTO;

import java.time.LocalDate;

public class OrderStatusDTO {
    private int orderId;
    private int customerId;
    private LocalDate orderDate;
    private double totalPrice;
    private boolean isPaid;
    private boolean isPickedUp;

    public OrderStatusDTO(int orderId, int customerId, LocalDate orderDate, double totalPrice, boolean isPaid, boolean isPickedUp) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.isPaid = isPaid;
        this.isPickedUp = isPickedUp;
    }

    //TODO: Last DTO to make endpoints for

    public int getOrderId() { return orderId; }
    public int getCustomerId() { return customerId; }
    public LocalDate getOrderDate() { return orderDate; }
    public double getTotalPrice() { return totalPrice; }
    public boolean isPaid() { return isPaid; }
    public boolean isPickedUp() { return isPickedUp; }
}
