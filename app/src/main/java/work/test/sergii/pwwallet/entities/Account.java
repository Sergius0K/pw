package work.test.sergii.pwwallet.entities;

/**
 * Created by sergii on 26.03.18.
 */

public class Account {

    public static String TABLENAME = "account";

    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String EMAIL = "email";
    public static String TOKEN = "token";
    public static String BALANCE = "balance";

    private String username;
    private String email;
    private String password;
    private String token;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void incrementBalance(double amount){
        this.balance += amount;
    }

    public void decrementBalance(double amount){
        this.balance -= amount;
    }
}
