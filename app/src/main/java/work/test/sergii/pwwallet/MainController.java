package work.test.sergii.pwwallet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import work.test.sergii.pwwallet.db.DBHelper;
import work.test.sergii.pwwallet.entities.Account;
import work.test.sergii.pwwallet.entities.Transaction;
import work.test.sergii.pwwallet.rest.RestClient;
import work.test.sergii.pwwallet.ui.activities.MainActivity;
import work.test.sergii.pwwallet.utils.JsonUtil;

/**
 * Created by sergii on 02.04.18.
 */

public class MainController {
    private static final String TAG = MainController.class.getSimpleName();

    private static MainController instance;

    private DBHelper dbHelper;
    private Account currentAccount;
    private RestClient restClient;

    private MainController(Context context) {
        dbHelper = DBHelper.getInstance(context);
        restClient = RestClient.getInstance();
    }

    public static MainController getInstance(Context context) {
        if (instance == null) {
            instance = new MainController(context);
        }

        return instance;
    }


    public Account getCurrentAccount() {

        currentAccount = dbHelper.getAllAccounts();
        return currentAccount;
    }

    public void registerAccount(Account account, Callback callback) {

        restClient.registerAccount(account, callback);
    }

    public void loginAccount(Account account, Callback callback) {

        restClient.login(account, callback);
    }

    public void updateAccount(final Account account, final Activity activity) {

        restClient.fetchAccountInfo(account, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream in = new BufferedInputStream(response.body().byteStream());

                try {
                    JSONObject jsonObject = JsonUtil.responceToJson(in);

                    if (jsonObject.has("user_info_token")) {

                        JSONObject userInfo = jsonObject.getJSONObject("user_info_token");

                        account.setUsername(userInfo.getString(JsonUtil.NAME));
                        account.setEmail(userInfo.getString(JsonUtil.EMAIL));
                        account.setBalance(userInfo.getDouble(JsonUtil.BALANCE));

                        dbHelper.insertAccount(account);

                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                    }
                } catch (JSONException ex) {
                    Log.d(TAG, ex.getMessage());
                }
            }
        });
    }

    public void refreshAccount(final Account account, final MainActivity activity) {

        restClient.fetchAccountInfo(account, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream in = new BufferedInputStream(response.body().byteStream());

                try {
                    JSONObject jsonObject = JsonUtil.responceToJson(in);

                    if (jsonObject.has("user_info_token")) {

                        JSONObject userInfo = jsonObject.getJSONObject("user_info_token");

                        account.setUsername(userInfo.getString(JsonUtil.NAME));
                        account.setEmail(userInfo.getString(JsonUtil.EMAIL));
                        account.setBalance(userInfo.getDouble(JsonUtil.BALANCE));

                        dbHelper.updateAccount(account);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                activity.refreshAccountInfo(account);
                            }
                        });
                    }
                } catch (JSONException ex) {
                    Log.d(TAG, ex.getMessage());
                }
            }
        });
    }

    public void fetchAllUsersList(String filter, Callback callback) {

        restClient.filterUsersList(currentAccount, filter, callback);
    }

    public void commitTransaction(String name, double amount, Callback callback) {

        restClient.createTransation(currentAccount, name, String.valueOf(amount), callback);
    }

    public void fetchAllTransactions(Callback callback) {

        restClient.getListOfTransactions(currentAccount, callback);
    }

    public void insertTransaction(Transaction transaction) {

        dbHelper.insertTransaction(transaction);
    }
}
