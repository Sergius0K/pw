package work.test.sergii.pwwallet.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import work.test.sergii.pwwallet.MainController;
import work.test.sergii.pwwallet.R;
import work.test.sergii.pwwallet.ui.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    private MainController mainController;

    private AutoCompleteTextView usersFilter;
    private ListView usersListView;
    private List<String> userList = new ArrayList<>();

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

        /*mainController.fetchAllUsersList(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });*/

        for(int i = 0; i < 20; i++ ) {
            userList.add("User " + String.valueOf(i));
        }

        usersListView.setAdapter(
                new ArrayAdapter<>(
                        getActivity(),
                        R.layout.support_simple_spinner_dropdown_item,
                        userList));

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO insert create transaction dialog
            }
        });

        return view;
    }

}
