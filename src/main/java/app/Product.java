package app;

import javax.persistence.*;

@Entity
@Table(name="product")
public class Product {

    @Id
    int id;

    @Column
    int price;

    @Column
    String name;

    @Column
    String description;

    @Column
    int ownerId;

    @Column
    String hidden;

    public Product(){

    }

    public Product( int id,int price, String name, String description, int ownerId, String hidden) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.hidden = hidden;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hidden='" + hidden +'\'' +
                ", ownerId='" + ownerId +'\'' +
                '}';
    }
}
