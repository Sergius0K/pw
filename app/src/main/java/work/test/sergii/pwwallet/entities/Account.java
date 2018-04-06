package work.test.sergii.pwwallet.entities;

import android.content.ContentValues;

import work.test.sergii.pwwallet.db.DBContract;

/**
 * Created by sergii on 26.03.18.
 */

public class Account {

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

    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();

        cv.put(DBContract.Account.USERNAME, this.username);
        cv.put(DBContract.Account.EMAIL, this.email);
        cv.put(DBContract.Account.PASSWORD, this.password);
        cv.put(DBContract.Account.BALANCE, this.balance);
        cv.put(DBContract.Account.TOKEN, this.token);

        return cv;
    }

}
