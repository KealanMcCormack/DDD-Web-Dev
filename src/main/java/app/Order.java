package app;

import javax.persistence.*;

@Entity
@Table(name="order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column
    String state;

    @Column
    int productId;

    @Column
    String customerUsername;


    public Order() { }

    public Order(String state, int productId, String customerUsername) {
        this.state = state;
        this.productId = productId;
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", products=" + productId +
                ", customerUsername='" + customerUsername + '\'' +
                '}';
    }
}