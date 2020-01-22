package com.taxi.friend.taxifriendclient.ui.driver

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taxi.friend.taxifriendclient.models.Driver
import com.taxi.friend.taxifriendclient.models.DriverData
import com.taxi.friend.taxifriendclient.models.ResponseWrapper
import com.taxi.friend.taxifriendclient.services.DriverService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class DriverViewModel(driverId: String) : ViewModel() {

    private val driver: MutableLiveData<DriverData> by lazy {
        MutableLiveData<DriverData>().also {
            loadDriver(driverId)
        }
    }


    fun getDriver(): LiveData<DriverData> {

        return driver
    }

    fun loadDriver(driverId: String){

            var pictures = ArrayList<String>()

            try {
                val service = DriverService()
                val callDriver = service.getDriver(driverId)

                callDriver.enqueue(object : Callback<Driver> {
                    override fun onResponse(call: Call<Driver>, response: Response<Driver>) {
                        var dr = response.body()
                        val name = dr?.name
                        val identityCar = dr?.carIdentity
                        val backCarPhoto = dr?.backCarPhoto.toString().replace("data:image/jpeg;base64,", "")
                        val frontCarPhoto = dr?.frontCarPhoto.toString().replace("data:image/jpeg;base64,", "")
                        pictures.add(backCarPhoto)
                        pictures.add(frontCarPhoto)
                        pictures.add(dr?.sideCarPhoto.toString())
                        driver.value = DriverData(name, identityCar, pictures)
                    }

                    override fun onFailure(call: Call<Driver>, t: Throwable) {
                        Log.e("ErrorGettingDriver", t.message)
                    }
                })
            } catch (ex: Exception) {
                Log.i("taxi", "taxi error")
            }
    }
}
