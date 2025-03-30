package app.DTO;

public class CupcakeDTO {
    private int cupcakeId;
    private String topName;
    private String bottomName;
    private double price;

    public CupcakeDTO(int cupcakeId, String topName, String bottomName, double price) {
        this.cupcakeId = cupcakeId;
        this.topName = topName;
        this.bottomName = bottomName;
        this.price = price;
    }

    // Getters
    public int getCupcakeId() { return cupcakeId; }
    public String getTopName() { return topName; }
    public String getBottomName() { return bottomName; }
    public double getPrice() { return price; }
}
