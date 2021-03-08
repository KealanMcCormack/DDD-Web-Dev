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
    int customerId;

    public List<Product> getCart() {
        return cart;
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }


    @OneToMany
    private List<Product> cart = new ArrayList<>();

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
