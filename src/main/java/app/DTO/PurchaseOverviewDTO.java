package app.DTO;

public class PurchaseOverviewDTO {

    private String bottomName;
    private String topName;
    private int quantity;
    private int totalPrice;
    private boolean isPaid;
    private boolean isPickedUp;

    public PurchaseOverviewDTO(String bottomName, String topName, int quantity, int totalPrice, boolean isPaid, boolean isPickedUp) {
        this.bottomName = bottomName;
        this.topName = topName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.isPaid = isPaid;
        this.isPickedUp = isPickedUp;
    }

    public String getBottomName() {
        return bottomName;
    }

    public String getTopName() {
        return topName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }
}
