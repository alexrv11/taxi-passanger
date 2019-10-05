package com.taxi.friend.taxifriendclient.ui.driver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.taxi.friend.taxifriendclient.models.DriverData

class DriverViewModel : ViewModel() {
    private var driver: MutableLiveData<DriverData>? = null

    fun getDriver(): LiveData<DriverData>? {

        if (driver == null) {
            driver = MutableLiveData()
            driver!!.value = DriverData("test name", "1234nkc")
        }

        return driver
    }
}
