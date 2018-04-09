package work.test.sergii.pwwallet.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import work.test.sergii.pwwallet.MainController;
import work.test.sergii.pwwallet.R;
import work.test.sergii.pwwallet.entities.Transaction;
import work.test.sergii.pwwallet.ui.activities.MainActivity;

import static work.test.sergii.pwwallet.utils.JsonUtil.jsonToTransactionList;

/**
 * Created by sergii on 09.04.18.
 */

public class TransactionUtil {

    public static void showTransactionCreateDialog(final MainActivity activity,
                                                   final double amount,
                                                   final String recipientUsername,
                                                   final MainController mainController) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_transaction_action, null);

        final TextView accountUsernameTextView = alertLayout.findViewById(R.id.account_username);
        final TextView accountAmountTextView = alertLayout.findViewById(R.id.account_amount);
        final TextView recipientUsernameTextView = alertLayout.findViewById(R.id.recipient_name);
        final EditText recipientAmount = alertLayout.findViewById(R.id.recipient_amount);

        accountUsernameTextView.setText(mainController.getCurrentAccount().getUsername());
        accountAmountTextView.setText(String.valueOf(mainController.getCurrentAccount().getBalance()));
        recipientUsernameTextView.setText(recipientUsername);
        recipientAmount.setText(String.valueOf(amount));

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(activity.getString(R.string.dialog_transaction_title));
        alert.setView(alertLayout);
        alert.setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (!recipientAmount.getText().toString().isEmpty()) {
                    double amount = Double.parseDouble(recipientAmount.getText().toString());

                    if (amount > mainController.getCurrentAccount().getBalance()) {
                        recipientAmount.setError(activity.getString(R.string.too_much));

                    } else {
                        mainController.commitTransaction(recipientUsername, amount, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                try {
                                    JSONObject jsonObject = JsonUtil.responceToJson(response.body().byteStream());

                                    Transaction tr = JsonUtil.parseJsonToTransaction(
                                            jsonObject.getJSONObject(JsonUtil.TRANSACTION_TOKEN));
                                    mainController.insertTransaction(tr);

                                    mainController.refreshAccount(mainController.getCurrentAccount(),activity);

                                } catch (JSONException ex) {
                                    ex.getMessage();
                                } catch (ParseException ex) {
                                    ex.getMessage();
                                }
                            }
                        });
                    }
                } else {
                    recipientAmount.setError(activity.getString(R.string.cant_be_empty));
                }
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }


    //2018-4-9 16:01:05 to long
    public static long getDateFromString(String dateString) throws ParseException {
        return new SimpleDateFormat("yyyy-M-d HH:mm:ss").parse(dateString).getTime();
    }

    //long to 16:01:05 09.04.2018
    public static String getStringFromDate(Long dateString) throws ParseException {
        return new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(dateString);
    }
}
