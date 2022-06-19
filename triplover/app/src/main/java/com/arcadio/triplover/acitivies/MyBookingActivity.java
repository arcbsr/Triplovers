package com.arcadio.triplover.acitivies;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.arcadio.triplover.BaseActivity;
import com.arcadio.triplover.R;
import com.arcadio.triplover.acitivies.ui.main.MyBookingFragment;

public class MyBookingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_booking_activity);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.menu_booking);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MyBookingFragment.newInstance())
                    .commitNow();
        }
    }


}