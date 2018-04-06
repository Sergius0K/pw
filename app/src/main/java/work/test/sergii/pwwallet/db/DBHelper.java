package work.test.sergii.pwwallet.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import work.test.sergii.pwwallet.entities.Account;
import work.test.sergii.pwwallet.entities.Transaction;
import work.test.sergii.pwwallet.entities.User;

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

    private String CREATE_USERS_TABLE = "CREATE TABLE " + DBContract.User.TABLENAME + "( " +
            DBContract.User.ID + " INTEGER UNSIGNED PRIMARY KEY," +
            DBContract.User.USERNAME + " TEXT NOT NULL," +
            DBContract.User.EMAIL + " TEXT NOT NULL," +
            DBContract.User.BALANCE + " REAL NOT NULL" + ")";

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
        db.execSQL(CREATE_USERS_TABLE);
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

    public long insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(DBContract.User.TABLENAME,  null , user.getContentValues());
    }

    public List<User> getAllUsers(){

        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(DBContract.Account.TABLENAME,
                null, null, null,
                null, null, null);
        User user;
        try {
            if (cursor.moveToFirst()) {
                do {
                    user = new User();
                    user.setUsername(cursor.getString(cursor.getColumnIndex(DBContract.User.USERNAME)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(DBContract.User.EMAIL)));
                    user.setBalance(cursor.getDouble(cursor.getColumnIndex(DBContract.User.BALANCE)));
                    user.setId(cursor.getString(cursor.getColumnIndex(DBContract.User.ID)));

                    users.add(user);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return users;
    }

    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(DBContract.User.TABLENAME,  user.getContentValues(), DBContract.User.ID+"= ?" , new String [] {user.getId()});
    }

    public void deleteUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBContract.User.TABLENAME,  DBContract.User.ID+"= ?" , new String [] {user.getId()});
    }

    public long insertTransaction(Transaction  transaction){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(DBContract.User.TABLENAME,  null , transaction.getContentValues());
    }

    public void updateUser(Transaction transaction){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(DBContract.User.TABLENAME,  transaction.getContentValues(), DBContract.User.ID+"= ?" , new String [] {String.valueOf(transaction.getTransactionId())});
    }

    public void deleteUser(Transaction transaction){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBContract.User.TABLENAME,  DBContract.User.ID+"= ?" , new String [] { String.valueOf(transaction.getTransactionId())});
    }
}
