package com.taxi.friend.taxifriendclient;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.taxi.friend.taxifriendclient.ui.driver.DriverFragment;

public class DriverActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DriverFragment.newInstance())
                    .commitNow();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Decile donde quieres ir");


    }
}
