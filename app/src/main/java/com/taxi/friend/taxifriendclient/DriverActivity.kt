package com.taxi.friend.taxifriendclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.android.gms.maps.model.LatLng
import com.taxi.friend.taxifriendclient.models.ClusterDriver
import com.taxi.friend.taxifriendclient.models.Driver
import com.taxi.friend.taxifriendclient.models.DriverLocation
import com.taxi.friend.taxifriendclient.models.ResponseWrapper
import com.taxi.friend.taxifriendclient.services.DriverService

import com.taxi.friend.taxifriendclient.ui.driver.DriverFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

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
        actionBar!!.title = "Solicita Tu Taxi"
    }


}
