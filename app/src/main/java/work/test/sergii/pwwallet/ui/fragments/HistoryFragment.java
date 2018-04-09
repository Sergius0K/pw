package work.test.sergii.pwwallet.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import work.test.sergii.pwwallet.MainController;
import work.test.sergii.pwwallet.R;
import work.test.sergii.pwwallet.entities.Transaction;
import work.test.sergii.pwwallet.ui.activities.MainActivity;
import work.test.sergii.pwwallet.ui.adapters.TransactionsAdapter;

/**
 *
 */
public class HistoryFragment extends Fragment {

    private MainController mainController;

    private ListView historyListView;
    private List<Transaction> transactionList = new ArrayList<>();

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        // Inflate the layout for this fragment

        mainController = ((MainActivity) getActivity()).getMainController();

        historyListView = view.findViewById(R.id.history_list);
        Transaction tempTr;

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {

                final Transaction currentTransaction = (Transaction) parent.getItemAtPosition(position);

                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                ad.setTitle(getContext().getString(R.string.you_want_copy_transaction));
                ad.setPositiveButton(getContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {

                        mainController.commitTransaction(
                                currentTransaction.getCorrespondentName(),
                                currentTransaction.getAmount(),
                                new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {

                                    }
                                });

                        Toast.makeText(getContext(), "Вы сделали правильный выбор",
                                Toast.LENGTH_LONG).show();
                    }
                });
                ad.setNegativeButton(getContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Toast.makeText(getContext(), "Возможно вы правы", Toast.LENGTH_LONG)
                                .show();
                    }
                });

                ad.show();
            }
        });

        for(int i = 0; i < 20; i++ ) {
            tempTr = new Transaction();

            tempTr.setCorrespondentName("User " + String.valueOf(i));
            tempTr.setTransactionId(i);
            tempTr.setAmount(200.0d);
            tempTr.setFinalBalance(300.d);
            tempTr.setTransationTime(new Date().getTime() + i);

            transactionList.add(tempTr );
        }

        final TransactionsAdapter transactionsAdapter = new TransactionsAdapter(
                getActivity(),
                R.layout.adapter_transaction_item,
                transactionList);



        mainController.fetchAllUsersList(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                // TODO необходимо распарсить ответ и заполнить список транзакций
                //transactionList

                transactionsAdapter.notifyDataSetChanged();
            }
        });

        historyListView.setAdapter(transactionsAdapter);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}