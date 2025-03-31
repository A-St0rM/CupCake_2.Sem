package app.entities;

public class CupcakeBottom {

    private int cupcakeBottomId;
    private String cupcakeBottomName;
    private int price;

    public CupcakeBottom(int cupcakeBottomId, int price, String cupcakeBottomName) {
        this.cupcakeBottomId = cupcakeBottomId;
        this.cupcakeBottomName = cupcakeBottomName;
        this.price = price;
    }
    public CupcakeBottom(int price, String cupcakeBottomName) {
        this.cupcakeBottomName = cupcakeBottomName;
        this.price = price;
    }

    public int getCupcakeBottomId() {
        return cupcakeBottomId;
    }

    public void setCupcakeBottomId(int cupcakeBottomId) {
        this.cupcakeBottomId = cupcakeBottomId;
    }

    public String getCupcakeBottomName() {
        return cupcakeBottomName;
    }

    public void setCupcakeBottomName(String cupcakeBottomName) {
        this.cupcakeBottomName = cupcakeBottomName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}


