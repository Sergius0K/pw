package work.test.sergii.pwwallet.ui.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import work.test.sergii.pwwallet.MainController;
import work.test.sergii.pwwallet.R;
import work.test.sergii.pwwallet.entities.Account;
import work.test.sergii.pwwallet.ui.fragments.HistoryFragment;
import work.test.sergii.pwwallet.ui.fragments.LoginFragment;
import work.test.sergii.pwwallet.ui.fragments.RegistrationFragment;
import work.test.sergii.pwwallet.ui.fragments.UsersFragment;

public class MainActivity extends AppCompatActivity {

    private MainController mainController;
    private Account account;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HistoryFragment historyFragment;

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

            historyFragment = new HistoryFragment();

            mainController.refreshAccount(account, this);

            viewPager = findViewById(R.id.viewpager);
            setupViewPager(viewPager);

            tabLayout = findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        StartActivity.ViewPagerAdapter adapter = new StartActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(historyFragment, getString(R.string.history));
        adapter.addFragment(new UsersFragment(), getString(R.string.users));
        viewPager.setAdapter(adapter);
    }

    public MainController getMainController(){
        return mainController;
    }

    public void refreshAccountInfo(Account account){
        username.setText(account.getUsername());
        email.setText(account.getEmail());
        balance.setText(
                getString(
                        R.string.balance,
                        String.valueOf(account.getBalance())));

        historyFragment.updateHistoryList();
    }
}
