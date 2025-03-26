package app.entities;

public class Customer {

    private int customer_id;
    private String email;
    private String password;
    private double balance;
    public Customer(int customer_id, String email, String password, double balance) {
        this.customer_id = customer_id;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
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
