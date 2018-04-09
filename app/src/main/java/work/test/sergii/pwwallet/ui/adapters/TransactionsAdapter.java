package work.test.sergii.pwwallet.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import work.test.sergii.pwwallet.R;
import work.test.sergii.pwwallet.entities.Transaction;
import work.test.sergii.pwwallet.ui.CreateTransactionDialog;

/**
 * Created by sergii on 07.04.18.
 */

public class TransactionsAdapter extends ArrayAdapter<Transaction> {


    public TransactionsAdapter(@NonNull Context context, int resource, @NonNull List<Transaction> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        if( convertView == null) {
            convertView = LayoutInflater.from(
                    getContext()).inflate(
                            R.layout.adapter_transaction_item,
                            null);
        }

        Transaction tr = getItem(position);

        TextView correspondentName = convertView.findViewById(R.id.transaction_correspondent_name);
        TextView dateTime = convertView.findViewById(R.id.transaction_date_time);
        TextView amount = convertView.findViewById(R.id.transaction_amount);
        TextView resultingBalance = convertView.findViewById(R.id.transaction_resulting_balance);

        correspondentName.setText(tr.getCorrespondentName());
        dateTime.setText(String.valueOf(tr.getTransationTime()));
        amount.setText(String.valueOf(tr.getAmount()));
        resultingBalance.setText(String.valueOf(tr.getFinalBalance()));


        return convertView;
    }
}
