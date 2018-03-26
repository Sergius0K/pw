package work.test.sergii.pwwallet.entities;

/**
 * Created by sergii on 26.03.18.
 */

public class User {

    public static String TABLENAME = "user";

    public static String ID = "id";
    public static String USERNAME = "username";
    public static String EMAIL = "email";
    public static String BALANCE = "balance";

    private String id;
    private String username;
    private String email;
    private double balance;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
