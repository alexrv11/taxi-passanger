package com.taxi.friend.taxifriendclient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.taxi.friend.taxifriendclient.ui.driver.DriverFragment;

public class DriverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DriverFragment.Companion.newInstance())
                    .commitNow();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Decile donde quieres ir");


    }
}
