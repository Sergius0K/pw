package work.test.sergii.pwwallet.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import work.test.sergii.pwwallet.MainController;
import work.test.sergii.pwwallet.R;
import work.test.sergii.pwwallet.entities.Account;

public class MainActivity extends AppCompatActivity {

    private MainController mainController;
    private Account account;

    private Toolbar toolbar;


    private TextView username;
    private TextView email;
    private TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainController = MainController.getInstance(getApplicationContext());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = findViewById(R.id.username_main);
        email = findViewById(R.id.email_main);
        balance = findViewById(R.id.balance_main);

    }

    @Override
    protected void onResume(){
        super.onResume();

        account = mainController.getCurrentAccount();

        if (account == null) {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
        } else {
            username.setText(account.getUsername());
            email.setText(account.getEmail());
            balance.setText(
                    getString(
                            R.string.balance,
                            String.valueOf(account.getBalance())));
        }
    }
}
