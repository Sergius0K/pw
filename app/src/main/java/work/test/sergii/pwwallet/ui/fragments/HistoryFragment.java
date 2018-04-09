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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import work.test.sergii.pwwallet.utils.JsonUtil;
import work.test.sergii.pwwallet.utils.TransactionUtil;

/**
 *
 */
public class HistoryFragment extends Fragment {

    private MainController mainController;

    private ListView historyListView;
    private List<Transaction> transactionList = new ArrayList<>();
    private TransactionsAdapter transactionsAdapter;

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

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
                Transaction tr =(Transaction) parent.getItemAtPosition(position);

                TransactionUtil.showTransactionCreateDialog((MainActivity) getActivity(),
                        Math.abs(tr.getAmount()),
                        tr.getCorrespondentName(),
                        mainController);
            }
        });

        transactionsAdapter  = new TransactionsAdapter(
                getActivity(),
                R.layout.adapter_transaction_item,
                transactionList);

        updateHistoryList();

        historyListView.setAdapter(transactionsAdapter);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void updateHistoryList(){
        mainController.fetchAllTransactions(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonArray = JsonUtil.responceToJson(response.body().byteStream());

                    transactionList.clear();
                    transactionList.addAll(
                            JsonUtil.jsonToTransactionList(
                                    jsonArray.getJSONArray(JsonUtil.TRANSACTION_TOKEN)));

                    Collections.sort(transactionList, new Comparator<Transaction>() {
                        @Override
                        public int compare(Transaction o1, Transaction o2) {

                            Date o1Date = new Date(o1.getTransationTime());
                            Date o2Date = new Date(o2.getTransationTime());

                            return o2Date.compareTo(o1Date);
                        }
                    });
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            transactionsAdapter.notifyDataSetChanged();
                        }
                    });

                } catch (JSONException ex) {
                    ex.getMessage();
                }
            }
        });
    }
}
