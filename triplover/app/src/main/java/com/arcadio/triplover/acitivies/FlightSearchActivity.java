package com.arcadio.triplover.acitivies;

import android.os.Bundle;

import androidx.navigation.ui.AppBarConfiguration;

import com.arcadio.triplover.BaseActivity;
import com.arcadio.triplover.R;
import com.arcadio.triplover.databinding.ActivityFlightSearchBinding;
import com.arcadio.triplover.utils.ImageLoader;

public class FlightSearchActivity extends BaseActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityFlightSearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFlightSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FlightSearchFragment.newInstance())
                    .commitNow();
        }
        setTitle(getString(R.string.app_name));
        ImageLoader.loadImageBackground(binding.homebackground, getContext(),R.drawable.home_background);
    }
}