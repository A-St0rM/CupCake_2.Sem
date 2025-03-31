package app.DTO;

public class CupcakeDTO {
    private int cupcakeId;
    private String topName;
    private String bottomName;
    private int price;
    private int quantity;

    public CupcakeDTO(int cupcakeId, String topName, String bottomName, int price, int quantity) {
        this.cupcakeId = cupcakeId;
        this.topName = topName;
        this.bottomName = bottomName;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters
    public int getCupcakeId() { return cupcakeId; }
    public String getTopName() { return topName; }
    public String getBottomName() { return bottomName; }
    public int getPrice() { return price; }

    public int getQuantity() {
        return quantity;
    }
}
