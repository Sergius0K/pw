package work.test.sergii.pwwallet.entities;

import android.content.ContentValues;

import work.test.sergii.pwwallet.db.DBContract;

/**
 * Created by sergii on 26.03.18.
 */

public class User {

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

    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();

        cv.put(DBContract.User.USERNAME, this.username);
        cv.put(DBContract.User.EMAIL, this.email);
        cv.put(DBContract.User.BALANCE, this.balance);

        return cv;
    }
}
