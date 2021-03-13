package app;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customer")
public class Customer {

    @Id
    String username;

    @Column
    private String password;

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int customerId;

    @OneToMany
    private List<Product> cart = new ArrayList<>();

    @OneToMany
    private List<Order> orderHistory = new ArrayList<>();

    public List<Product> getCart() {
        return cart;
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }

    public void addToCart(Product product){
        cart.add(product);
    }
    public Customer(){

    }

    public Customer(String username, String password){
        this.username = username;
        this.password = password;
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

    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", customerId=" + customerId +
                '}';
    }

    public int totalPrice(){

        int total = 0;

        for(int i = 0; i < cart.size(); i++){
            total += getCart().get(i).getPrice();
        }

        return total;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public void removeOrder(Order order) {
        this.orderHistory.remove(order);
    }

    public void addOrder(Order order){
        orderHistory.add(order);
    }
}
