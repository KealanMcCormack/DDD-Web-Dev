package app;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column
    String state;

    @OneToMany
    private List<Product> products = new ArrayList<>();

    @Column
    String customerUsername;


    public Order() { }

    public Order(String state, List<Product> products, String customerUsername) {
        this.state = state;
        this.products = products;
        this.customerUsername = customerUsername;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", products=" + products +
                ", customerUsername='" + customerUsername + '\'' +
                '}';
    }
}