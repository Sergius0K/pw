package work.test.sergii.pwwallet.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import work.test.sergii.pwwallet.R;
import work.test.sergii.pwwallet.entities.Account;

/**
 * Created by sergii on 09.04.18.
 */

public class CreateTransactionDialog extends Dialog {

    private String recipientName;
    private Account currentAccount;

    public CreateTransactionDialog(@NonNull Context context) {
        super(context);
    }

    public void setGeneralInfo(Account currentAccount, String recipientName, OnClickListener positive) {
        this.recipientName = recipientName;
        this.currentAccount = currentAccount;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_transaction_action);

        TextView recipientName = findViewById(R.id.recipient_name);
        TextView accountUsername = findViewById(R.id.account_username);
        TextView accountAmount = findViewById(R.id.account_amount);
        EditText recipientAmount = findViewById(R.id.recipient_amount);

    }
}
