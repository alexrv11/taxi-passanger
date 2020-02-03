package com.taxi.friend.taxifriendclient.repositories

import com.taxi.friend.taxifriendclient.models.Driver
import com.taxi.friend.taxifriendclient.models.DriverLocation
import com.taxi.friend.taxifriendclient.models.ResponseWrapper

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DriverRepository {

    @GET("driverlocations")
    fun getDrivers(@Query("radio") radio: Double,
                   @Query("latitude") latitude: Double, @Query("longitude") longitude: Double): Call<ResponseWrapper<List<DriverLocation>>>

    @GET("drivers/{driverId}")
    fun getDriver(@Path("driverId") id: String): Call<Driver>

}
