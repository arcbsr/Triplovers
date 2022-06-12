package com.arcadio.triplover;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.arcadio.triplover.acitivies.MyBookingActivity;
import com.arcadio.triplover.databinding.ActivityMainBinding;
import com.arcadio.triplover.fragments.LoginDialogFragment;
import com.arcadio.triplover.models.usermodel.LoginResponse;
import com.arcadio.triplover.utils.KLog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener;

import java.util.Random;

public class MainActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_booking)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                navController.navigate(R.id.nav_home);
                drawer.close();
                return true;
            }else if (item.getItemId() == R.id.nav_booking) {
                startActivity(new Intent(getContext(), MyBookingActivity.class));
                drawer.close();
                return false;
            } else if (item.getItemId() == R.id.nav_about) {
                drawer.close();
                return true;
            } else if (item.getItemId() == R.id.nav_contact) {

                return true;
            } else if (item.getItemId() == R.id.nav_loginuser) {
                drawer.close();
//                navController.navigate(R.id.nav_login);
                new LoginDialogFragment(new LoginDialogFragment.Listener() {
                    @Override
                    public void onLogIn(LoginResponse response) {
                        KLog.w(response == null ? "Error in data" : new Gson().toJson(response));
                    }

                    @Override
                    public void onLoginFailed() {
                        new MaterialAlertDialogBuilder(MainActivity.this)
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
            return false;
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}