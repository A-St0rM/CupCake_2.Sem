package app.entities;

public class CupcakeTop {

    private int cupcakeTopId;
    private String cupcakeTopName;
    private int price;

    public CupcakeTop(int price, String cupcakeTopName) {
        this.cupcakeTopName = cupcakeTopName;
        this.price = price;
    }

    public CupcakeTop(int cupcakeTopId, int price, String cupcakeTopName) {
        this.cupcakeTopId = cupcakeTopId;
        this.cupcakeTopName = cupcakeTopName;
        this.price = price;
    }


    public int getCupcakeTopId() {
        return cupcakeTopId;
    }

    public void setCupcakeTopId(int cupcakeTopId) {
        this.cupcakeTopId = cupcakeTopId;
    }

    public String getCupcakeTopName() {
        return cupcakeTopName;
    }

    public void setCupcakeTopName(String cupcakeTopName) {
        this.cupcakeTopName = cupcakeTopName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "CupcakeTop{" +
                "cupcake_top_id=" + cupcakeTopId +
                ", cupcake_top_name='" + cupcakeTopName + '\'' +
                ", price=" + price +
                '}';
    }
}
