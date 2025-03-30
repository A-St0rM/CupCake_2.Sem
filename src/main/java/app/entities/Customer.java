package app.entities;

public class Customer {

    private int customerId;
    private String email;
    private String password;
    private double balance;

    public Customer(int customerId, String email, String password, double balance) {
        this.customerId = customerId;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }
    public Customer(String email, String password, double balance) {
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
