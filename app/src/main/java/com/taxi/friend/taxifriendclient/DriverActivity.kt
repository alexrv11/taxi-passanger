package com.taxi.friend.taxifriendclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.taxi.friend.taxifriendclient.ui.driver.DriverFragment

class DriverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.driver_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DriverFragment.newInstance())
                    .commitNow()
        }

        val actionBar = supportActionBar
        actionBar!!.title = "Decile donde quieres ir"


    }
}
