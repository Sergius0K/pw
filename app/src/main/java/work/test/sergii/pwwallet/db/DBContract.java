package work.test.sergii.pwwallet.db;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import work.test.sergii.pwwallet.BuildConfig;

/**
 * Created by sergii on 30.03.18.
 */

public class DBContract {

    public interface Account extends BaseColumns {

        public String TABLENAME = "account";

        public String USERNAME = "username";
        public String PASSWORD = "password";
        public String EMAIL = "email";
        public String TOKEN = "token";
        public String BALANCE = "balance";
    }

    public interface User extends BaseColumns {

        public String TABLENAME = "users";

        public String ID = "id";
        public String USERNAME = "username";
        public String EMAIL = "email";
        public String BALANCE = "balance";
    }

    public interface Transaction extends BaseColumns {

        public String TABLENAME = "transactions";

        public String ID = "id";
        public String TRANSACTION_TIME = "transation_time";
        public String CORRESPONDENT_ID = "correspondent_id";
        public String AMOUNT = "amount";
        public String BALANCE = "balance";
    }

}
