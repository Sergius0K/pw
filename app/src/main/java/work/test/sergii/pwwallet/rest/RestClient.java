package work.test.sergii.pwwallet.rest;

import android.util.Log;

import org.json.JSONException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import work.test.sergii.pwwallet.JsonUtil;
import work.test.sergii.pwwallet.entities.Account;
import work.test.sergii.pwwallet.entities.User;

/**
 * Created by sergii on 01.04.18.
 */

public class RestClient {

    private static final String TAG = RestClient.class.getSimpleName();

    private final static String BASE_URL = "http://193.124.114.46:3001/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static RestClient client;
    private static OkHttpClient restClient;
    // TODO просто рест клиент отправляющий запросы серверу...

    private RestClient() {
        restClient = new OkHttpClient();
    }

    public static RestClient getInstance() {
        if (client == null) {
            client = new RestClient();
        }

        return client;
    }


    public boolean registerAccount(Account account, Callback callback) {

        try {
            String postBody = JsonUtil.toJsonRegistrationString(account);

            RequestBody body = RequestBody.create(JSON, postBody);

            Request request = new Request.Builder()
                    .url(BASE_URL + "users")
                    .post(body)
                    .build();

            restClient.newCall(request).enqueue(callback);

            return true;
        } catch (JSONException ex) {
            Log.d(TAG, ex.getMessage());
        }

        return false;
    }


    public boolean fetchAccountInfo(Account account, Callback callback) {

        if (account.getToken() == null || account.getToken().isEmpty()) {
            return false;
        } else {

            Request request = new Request.Builder()
                    .url(BASE_URL + "api/protected/user-info")
                    .addHeader("Authorization", "Bearer " + account.getToken())
                    .build();

            restClient.newCall(request).enqueue(callback);

            return true;
        }
    }

    /**
     * Login
     * POST /sessions/create
     *
     * @param account
     * @param callback
     * @return
     */
    public boolean login(Account account, Callback callback) {

        try {
            String postBody = JsonUtil.toJsonLoginString(account);

            RequestBody body = RequestBody.create(JSON, postBody);

            Request request = new Request.Builder()
                    .url(BASE_URL + "sessions/create")
                    .post(body)
                    .build();

            restClient.newCall(request).enqueue(callback);

            return true;
        } catch (JSONException ex) {
            Log.d(TAG, ex.getMessage());
        }

        return false;
    }

    /**
     * List of logged user transactions
     * GET /api/protected/transactions
     * authentication: bearer
     * body:
     * returns:
     * {trans_token:[{id, date, username, amount, balance}]}
     * errors:
     * 401: UnauthorizedError
     * 401: Invalid user
     */
    public boolean getListOfTransactions(Account account, Callback callback) {

        if (account.getToken().isEmpty()) {
            return false;
        } else {

            Request request = new Request.Builder()
                    .url(BASE_URL + "api/protected/transactions")
                    .addHeader("Authorization", "Bearer " + account.getToken())
                    .build();

            restClient.newCall(request).enqueue(callback);

            return true;
        }
    }

    /**
     * Create a transaction
     * Sender: logged user
     * Recipient: name in a body
     * POST /api/protected/transactions
     * authentication: bearer
     * body:
     * {name, amount}
     * returns:
     * {trans_token:{id, date, username, amount, balance}}
     * errors:
     * 400: user not found
     * 400: balance exceeded
     * 401: UnauthorizedError
     * 401: Invalid user
     */
    public boolean createTransation(Account account, String userName, String amount, Callback callback) {

        if (account.getToken().isEmpty()) {
            return false;
        } else {
            try {
                String postBody = JsonUtil.toJsonStartTransation(userName, amount);

                RequestBody body = RequestBody.create(JSON, postBody);

                Request request = new Request.Builder()
                        .url(BASE_URL + "api/protected/transactions")
                        .post(body)
                        .addHeader("Authorization", "Bearer " + account.getToken())
                        .build();

                restClient.newCall(request).enqueue(callback);

                return true;
            } catch (JSONException ex) {
                Log.d(TAG, ex.getMessage());
            }
        }

        return false;
    }

    /**
     * Filtered User list
     * POST /api/protected/users/list
     * authentication: bearer
     * body:
     * {filter}
     * returns:
     * [{id, name}]
     * errors:
     * 401: UnauthorizedError
     * 401: Invalid user
     * 401: No search string
     */
    public boolean filterUsersList(Account account, String filter, Callback callback) {

        if (account.getToken().isEmpty()) {
            return false;
        } else {
            try {
                String postBody = JsonUtil.toJsonFilter(filter);

                RequestBody body = RequestBody.create(JSON, postBody);

                Request request = new Request.Builder()
                        .url(BASE_URL + "api/protected/users/list")
                        .post(body)
                        .addHeader("Authorization", "Bearer " + account.getToken())
                        .build();

                restClient.newCall(request).enqueue(callback);

                return true;
            } catch (JSONException ex) {
                Log.d(TAG, ex.getMessage());
            }
        }

        return false;
    }
}
