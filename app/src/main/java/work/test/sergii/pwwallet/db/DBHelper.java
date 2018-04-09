package work.test.sergii.pwwallet.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import work.test.sergii.pwwallet.entities.Account;
import work.test.sergii.pwwallet.entities.Transaction;

/**
 * Created by sergii on 26.03.18.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    private static int DB_VERSION = 1;
    private static String DB_NAME = "pw_wallet";

    private Context context;

    private String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + DBContract.Account.TABLENAME + " ( " +
            DBContract.Account.EMAIL + " TEXT PRIMARY KEY," +
            DBContract.Account.USERNAME + " TEXT NOT NULL," +
            DBContract.Account.PASSWORD + " TEXT NOT NULL," +
            DBContract.Account.BALANCE + " REAL NOT NULL," +
            DBContract.Account.TOKEN + " TEXT NOT NULL" + ")";

    private String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + DBContract.Transaction.TABLENAME + "( " +
            DBContract.Transaction.ID + " INTEGER UNSIGNED PRIMARY KEY," +
            DBContract.Transaction.CORRESPONDENT_ID + " TEXT NOT NULL," +
            DBContract.Transaction.AMOUNT + " REAL NOT NULL," +
            DBContract.Transaction.BALANCE + " REAL NOT NULL," +
            DBContract.Transaction.TRANSACTION_TIME + " INTEGER NOT NULL" + ")";


    public static DBHelper instance;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        this.context = context;

        // This will happen in onConfigure for API >= 16
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            SQLiteDatabase db = getWritableDatabase();
            db.enableWriteAheadLogging();
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    public static DBHelper getInstance(Context context) {

        if (instance == null) {
            instance = new DBHelper(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DBContract.Account.TABLENAME,  null ,account.getContentValues());
    }

    public void updateAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(DBContract.Account.TABLENAME,  account.getContentValues(), null , null);
    }

    public Account getAllAccounts(){

        Account account = null;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(DBContract.Account.TABLENAME,
                null, null, null,
                null, null, null);

        try {
            if (cursor.moveToFirst()) {
                    account = new Account();
                    account.setUsername(cursor.getString(cursor.getColumnIndex(DBContract.Account.USERNAME)));
                    account.setPassword(cursor.getString(cursor.getColumnIndex(DBContract.Account.PASSWORD)));
                    account.setEmail(cursor.getString(cursor.getColumnIndex(DBContract.Account.EMAIL)));
                    account.setBalance(cursor.getDouble(cursor.getColumnIndex(DBContract.Account.BALANCE)));
                    account.setToken(cursor.getString(cursor.getColumnIndex(DBContract.Account.TOKEN)));
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return account;
    }

    public void deleteAccount(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBContract.Account.TABLENAME,  null, null );
    }



    public long insertTransaction(Transaction  transaction){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(DBContract.Transaction.TABLENAME,  null , transaction.getContentValues());
    }

    public void updateTransaction(Transaction transaction){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(DBContract.Transaction.TABLENAME,  transaction.getContentValues(), DBContract.Transaction.ID+"= ?" , new String [] {String.valueOf(transaction.getTransactionId())});
    }

    public void deleteTransaction(Transaction transaction){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBContract.Transaction.TABLENAME,  DBContract.Transaction.ID+"= ?" , new String [] { String.valueOf(transaction.getTransactionId())});
    }
}
