package com.taxi.friend.taxifriendclient.repositories;

import com.taxi.friend.taxifriendclient.models.Location;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserRepository {

    @PUT("users/{userId}/location")
    Call<String> updateLocation(@Path("userId") String driverId, @Body Location location);

}
