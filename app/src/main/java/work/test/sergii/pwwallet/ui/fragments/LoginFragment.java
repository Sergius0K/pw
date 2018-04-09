package work.test.sergii.pwwallet.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import static work.test.sergii.pwwallet.utils.ResponseUtil.showErrorMessage;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private TextView email;
    private TextView password;
    private Button loginButton;

    private MainController mainController;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mainController = ((StartActivity) getActivity()).getMainController();

        email = view.findViewById(R.id.email_login);
        password = view.findViewById(R.id.password_login);
        loginButton = view.findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    final Account account = new Account();

                    account.setEmail(email.getText().toString());
                    account.setPassword(password.getText().toString());

                    mainController.loginAccount(account, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                            Log.d(TAG, e.getMessage());
                            ResponseUtil.showErrorMessage(e.getMessage(), getActivity());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.code() == HttpURLConnection.HTTP_OK || response.code() == HttpURLConnection.HTTP_CREATED) {
                                InputStream in =
                                        new BufferedInputStream(response.body().byteStream());

                                try {
                                    JSONObject jsonObject = JsonUtil.responceToJson(in);
                                    if (jsonObject.has("id_token")) {
                                        account.setToken(jsonObject.getString("id_token"));
                                        mainController.updateAccount(account, getActivity());
                                    }
                                } catch (JSONException ex) {
                                    Log.d(TAG, ex.getMessage());
                                }
                            } else if (response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {

                                String errorMessage =
                                        ResponseUtil.responceToString(response.body().byteStream());

                                ResponseUtil.showErrorMessage(errorMessage, getActivity());

                            } if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {

                                String errorMessage =
                                        ResponseUtil.responceToString(response.body().byteStream());

                                ResponseUtil.showErrorMessage(errorMessage, getActivity());
                            }
                        }
                    });

                } else {
                    if (email.getText().toString().isEmpty()) {
                        email.setError(getString(R.string.cant_be_empty));
                    }
                    if (password.getText().toString().isEmpty()) {
                        password.setError(getString(R.string.cant_be_empty));
                    }
                }
            }
        });

        return view;
    }


}
