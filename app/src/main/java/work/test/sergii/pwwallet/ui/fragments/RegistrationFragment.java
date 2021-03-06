package work.test.sergii.pwwallet.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import work.test.sergii.pwwallet.utils.JsonUtil;
import work.test.sergii.pwwallet.MainController;
import work.test.sergii.pwwallet.R;
import work.test.sergii.pwwallet.entities.Account;
import work.test.sergii.pwwallet.ui.activities.StartActivity;
import work.test.sergii.pwwallet.utils.ResponseUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {


    private static final String TAG = RegistrationFragment.class.getSimpleName();

    private TextView username;
    private TextView email;
    private TextView password;
    private TextView rPassword;
    private Button registerButton;

    private MainController mainController;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        mainController = ((StartActivity) getActivity()).getMainController();

        username = view.findViewById(R.id.username_login);
        email = view.findViewById(R.id.email_login);
        password = view.findViewById(R.id.password_login);
        rPassword = view.findViewById(R.id.reenter_password_login);
        registerButton = view.findViewById(R.id.registration_action);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rPassword.getText().toString().equals(password.getText().toString())) {

                    final Account account = new Account();
                    account.setUsername(username.getText().toString());
                    account.setEmail(email.getText().toString());
                    account.setPassword(password.getText().toString());

                    mainController.registerAccount(account, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                            Log.d(TAG, e.getMessage());
                            ResponseUtil.showErrorMessage(e.getMessage(), getActivity());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            if (response.code() == HttpURLConnection.HTTP_OK || response.code() == HttpURLConnection.HTTP_CREATED) {
                                InputStream in = new BufferedInputStream(response.body().byteStream());

                                try {
                                    JSONObject jsonObject = JsonUtil.responceToJson(in);
                                    if (jsonObject.has("id_token")) {
                                        account.setToken(jsonObject.getString("id_token"));
                                    }
                                } catch (JSONException ex) {
                                    Log.d(TAG, ex.getMessage());
                                }

                                mainController.updateAccount(account,getActivity());

                            } else if (response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {

                                String errorMessage =
                                        ResponseUtil.responceToString(response.body().byteStream());

                                ResponseUtil.showErrorMessage(errorMessage, getActivity());

                            }
                        }
                    });

                } else {
                    rPassword.setError(getString(R.string.password_do_not_match));
                    password.setError(getString(R.string.password_do_not_match));
                }
            }
        });

        return view;
    }

}
