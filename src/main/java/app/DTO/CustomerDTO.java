package app.DTO;

public class CustomerDTO {

    private int customerId;
    private String email;
    private double balance;

    public CustomerDTO(int customerId, String email, double balance) {
        this.customerId = customerId;
        this.email = email;
        this.balance = balance;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }
}
