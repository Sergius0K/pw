package work.test.sergii.pwwallet.entities;

/**
 * Created by sergii on 26.03.18.
 */

public class Transaction {

    public static String TABLENAME = "transaction";

    public static String ID = "id";
    public static String TRANSACTION_TIME = "transation_time";
    public static String CORRESPONDENT_NAME = "correspondent_name";
    public static String AMOUNT = "amount";
    public static String BALANCE = "balance";

    private int transactionId;
    private long transationTime;
    private String correspondentName;
    private double amount;
    private double finalBalance;

    public long getTransationTime() {
        return transationTime;
    }

    public void setTransationTime(long transationTime) {
        this.transationTime = transationTime;
    }

    public String getCorrespondentName() {
        return correspondentName;
    }

    public void setCorrespondentName(String correspondentName) {
        this.correspondentName = correspondentName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(double finalBalance) {
        this.finalBalance = finalBalance;
    }
}
