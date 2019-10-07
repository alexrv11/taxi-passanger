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
            var pictures = ArrayList<String>()
            pictures.add("picture1")
            pictures.add("picture2")
            pictures.add("picture3")
            pictures.add("picture4")
            pictures.add("picture5")
            pictures.add("picture6")
            pictures.add("picture7")
            pictures.add("picture8")
            pictures.add("picture9")
            driver!!.value = DriverData("test name", "1234nkc", pictures)
        }

        return driver
    }
}
