package com.taxi.friend.taxifriendclient.repositories;

import com.taxi.friend.taxifriendclient.models.DriverLocation;
import com.taxi.friend.taxifriendclient.models.ResponseWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DriverRepository {

    @GET("drivers")
    Call<ResponseWrapper<List<DriverLocation>>> getDrivers(@Query("radio") double radio,
                                                           @Query("latitude") double latitude, @Query("longitude") double longitude);

}
