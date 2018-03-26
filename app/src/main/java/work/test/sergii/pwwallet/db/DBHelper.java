package work.test.sergii.pwwallet.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import work.test.sergii.pwwallet.entities.Account;
import work.test.sergii.pwwallet.entities.Transaction;
import work.test.sergii.pwwallet.entities.User;

/**
 * Created by sergii on 26.03.18.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;

    private String CREATE_ACCOUNT_TABLE = "CREATE " + Account.TABLENAME + "( " +
            Account.EMAIL + " TEXT PRIMARY KEY," +
            Account.USERNAME + " TEXT NOT NULL," +
            Account.PASSWORD + " TEXT NOT NULL," +
            Account.BALANCE + " REAL NOT NULL," +
            Account.TOKEN + " TEXT NOT NULL" + ")";

    private String CREATE_TRANSACTION_TABLE = "CREATE " + Transaction.TABLENAME + "( " +
            Transaction.ID + " INTEGER UNSIGNED PRIMARY KEY," +
            Transaction.CORRESPONDENT_NAME + " TEXT NOT NULL," +
            Transaction.AMOUNT + " REAL NOT NULL," +
            Transaction.BALANCE + " REAL NOT NULL," +
            Transaction.TRANSACTION_TIME + " INTEGER NOT NULL" + ")";

    private String CREATE_USERS_TABLE = "CREATE " + User.TABLENAME + "( " +
            User.ID + " INTEGER UNSIGNED PRIMARY KEY," +
            User.USERNAME + " TEXT NOT NULL," +
            User.EMAIL + " TEXT NOT NULL," +
            User.BALANCE + " REAL NOT NULL" + ")";


    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "pw_db", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // TODO описать создание базы данных

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
