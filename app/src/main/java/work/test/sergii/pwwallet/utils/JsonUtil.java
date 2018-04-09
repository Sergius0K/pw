package work.test.sergii.pwwallet.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import work.test.sergii.pwwallet.entities.Account;
import work.test.sergii.pwwallet.entities.Transaction;

/**
 * Created by sergii on 02.04.18.
 */

public class JsonUtil {

    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String BALANCE = "balance";
    public static final String PASSWORD = "password";
    public static final String AMOUNT = "amount";
    public static final String TRANSACTION_ID = "transaction_id";
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String TRANSACTION_TOKEN = "trans_token";

    /**
     * Get json like
     * {"username":"John Doo","password":"johnpwd","email":"john@doo.foo"}
     *
     * @param account
     * @return
     */
    public static String toJsonRegistrationString(Account account) throws JSONException {
        JSONObject sb = new JSONObject();

        sb.put(JsonUtil.USERNAME, account.getUsername());
        sb.put(JsonUtil.PASSWORD, account.getPassword());
        sb.put(JsonUtil.EMAIL, account.getEmail());

        return sb.toString();
    }

    /**
     * Get json like
     * {"email":"john@doo.foo","password":"johnpwd"}
     *
     * @param account
     * @return
     */
    public static String toJsonLoginString(Account account) throws JSONException {
        JSONObject sb = new JSONObject();

        sb.put(JsonUtil.EMAIL, account.getEmail());
        sb.put(JsonUtil.PASSWORD, account.getPassword());

        return sb.toString();
    }

    public static String toJsonStartTransation(String userName, String amount) throws JSONException {
        JSONObject sb = new JSONObject();

        sb.put(JsonUtil.NAME, userName);
        sb.put(JsonUtil.AMOUNT, amount);

        return sb.toString();
    }

    public static String toJsonFilter(String filter) throws JSONException {
        JSONObject sb = new JSONObject();

        sb.put("filter", filter);

        return sb.toString();
    }

    public static JSONObject responceToJson(InputStream is) throws JSONException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new JSONObject(sb.toString());
    }

    public static List<String> jsonToUsersList(JSONArray jsonArray) {

        List<String> usersList = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                usersList.add(jsonArray.getJSONObject(i).getString(JsonUtil.NAME));
            }
        } catch (JSONException ex) {
            ex.getMessage();
        }

        return usersList;
    }

    public static List<Transaction> jsonToTransactionList(JSONArray jsonArray) {

        List<Transaction> usersList = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                usersList.add(parseJsonToTransaction(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException ex) {
            ex.getMessage();
        } catch (ParseException ex) {
            ex.getMessage();
        }

        return usersList;
    }

    public static JSONArray responceToJsonArray(InputStream is) throws JSONException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new JSONArray(sb.toString());
    }

    public static Transaction parseJsonToTransaction(JSONObject obj) throws JSONException, ParseException {
        Transaction tempTr = new Transaction();

        tempTr.setTransactionId(obj.getInt(JsonUtil.ID));

        tempTr.setTransationTime(TransactionUtil.getDateFromString(obj.getString(JsonUtil.DATE)));
        tempTr.setFinalBalance(obj.getInt(JsonUtil.BALANCE));
        tempTr.setCorrespondentName(obj.getString(JsonUtil.USERNAME));
        tempTr.setAmount(obj.getDouble(JsonUtil.AMOUNT));

        return tempTr;
    }


}
