package app;

import javax.persistence.*;

@Entity
@Table(name="owner")
public class Owner extends Customer {

    @Id
    String username;

    @Column
    private String password;

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ownerId;

    public Owner(){

    }

    public Owner(String username, String password){
        super(username, password);
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public int getOwnerId() { return ownerId; }

    @Override
    public String toString() {
        return "Owner{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}
