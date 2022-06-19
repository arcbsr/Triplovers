package com.arcadio.triplover.acitivies;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.arcadio.triplover.BaseActivity;
import com.arcadio.triplover.R;
import com.arcadio.triplover.acitivies.ui.main.MyBookingFragment;
import com.arcadio.triplover.fragments.LoginDialogFragment;
import com.arcadio.triplover.models.usermodel.LoginResponse;
import com.arcadio.triplover.ui.home.HomeFragment;
import com.arcadio.triplover.utils.KLog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

public class MainTabActivity extends BaseActivity {
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();


    }

    int selectedFragmentId = R.id.nav_home;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragmentId = R.id.nav_home;
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_booking:
                    selectedFragmentId = R.id.nav_booking;
                    selectedFragment = new MyBookingFragment();//MyBookingFragment
                    break;
                case R.id.nav_loginr:
                    new LoginDialogFragment(new LoginDialogFragment.Listener() {
                        @Override
                        public void onLogIn(LoginResponse response) {
                            KLog.w(response == null ? "Error in data" : new Gson().toJson(response));
                        }

                        @Override
                        public void onLoginFailed() {
                            new MaterialAlertDialogBuilder(MainTabActivity.this)
                                    .setTitle("Falied")
                                    .setMessage(getString(R.string.loginFailed))
                                    .setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .show();
                        }
                    }).show(getSupportFragmentManager(), "LoginFrom");
                    return false;
            }
            if (selectedFragment == null) {
                return false;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        if (selectedFragmentId != R.id.nav_home) {
            bottomNav.setSelectedItemId(R.id.nav_home);
            return;
        }
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(getString(R.string.exit))
                .setMessage(getString(R.string.exit_msg))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainTabActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}