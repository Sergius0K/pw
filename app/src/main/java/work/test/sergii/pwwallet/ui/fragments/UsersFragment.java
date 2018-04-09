package work.test.sergii.pwwallet.ui.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import work.test.sergii.pwwallet.MainController;
import work.test.sergii.pwwallet.R;
import work.test.sergii.pwwallet.ui.CreateTransactionDialog;
import work.test.sergii.pwwallet.ui.activities.MainActivity;
import work.test.sergii.pwwallet.utils.JsonUtil;
import work.test.sergii.pwwallet.utils.ResponseUtil;
import work.test.sergii.pwwallet.utils.TransactionUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    private double BASIC_AMOUNT = 0.0;

    private MainController mainController;

    private EditText usersFilter;
    private ListView usersListView;
    private List<String> userList = new ArrayList<>();
    private ArrayAdapter<String> currentAdapter;

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        mainController = ((MainActivity) getActivity()).getMainController();
        usersListView = view.findViewById(R.id.users_list_view);
        usersFilter = view.findViewById(R.id.recipient_name_filter);

        currentAdapter  = new ArrayAdapter<>(
                getActivity(),
                R.layout.support_simple_spinner_dropdown_item,
                userList);


        updateUsersList("");

        usersFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateUsersList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        usersListView.setAdapter(currentAdapter);

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TransactionUtil.showTransactionCreateDialog(getActivity(),
                        BASIC_AMOUNT,
                        (String)parent.getItemAtPosition(position),
                        mainController);

            }
        });

        return view;
    }

    private void updateUsersList(String filter){

        mainController.fetchAllUsersList(filter,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.code() == HttpURLConnection.HTTP_OK || response.code() == HttpURLConnection.HTTP_CREATED) {
                    try {
                        JSONArray jsonArray = JsonUtil.responceToJsonArray(response.body().byteStream());

                        userList.clear();
                        userList.addAll(JsonUtil.jsonToUsersList(jsonArray));

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                currentAdapter.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException ex) {
                        ex.getMessage();
                    }
                } else if (response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {

                    String errorMessage =
                            ResponseUtil.responceToString(response.body().byteStream());

                    ResponseUtil.showErrorMessage(errorMessage, getActivity());
                }

            }
        });
    }
}
