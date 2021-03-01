package app;

public class Owner {

    String username;
    private String password;
    int ownerId;

    public Owner(){

    }

    public Owner(String username, String password, int ownerId){
        this.username = username;
        this.password = password;
        this.ownerId = ownerId;
    }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public int getOwnerId() { return ownerId; }

}
