package app;

import java.util.ArrayList;

public class Customer {

    String username;
    private String password;
    int customerId;

    public ArrayList<Product> getCart() {
        return cart;
    }

    public void setCart(ArrayList<Product> cart) {
        this.cart = cart;
    }

    private ArrayList<Product> cart = new ArrayList<>();

    public void addToCart(Product product){
        cart.add(product);
    }
    public Customer(){

    }

    public Customer(String username, String password, int customerId){
        this.username = username;
        this.password = password;
        this.customerId = customerId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getCustomerId() {
        return customerId;
    }
}
