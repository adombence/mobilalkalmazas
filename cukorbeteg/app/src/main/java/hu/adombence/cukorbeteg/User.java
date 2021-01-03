package hu.adombence.cukorbeteg;

public class User {
    private final String username;
    private final String email;
    private final int id;


    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }
}
