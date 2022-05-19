package com.arcadio.triplover.acitivies;

import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.arcadio.triplover.BaseActivity;
import com.arcadio.triplover.R;
import com.arcadio.triplover.databinding.ActivityFlightSearchBinding;

public class FlightSearchActivity extends BaseActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityFlightSearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFlightSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_flight_search);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!NavigationUI.navigateUp(navController, appBarConfiguration)){
                    finish();
                }else {
                    navController.popBackStack();
                }
            }
        });
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_flight_search);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}