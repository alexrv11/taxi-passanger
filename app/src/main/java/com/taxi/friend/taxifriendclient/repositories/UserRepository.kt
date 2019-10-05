package com.taxi.friend.taxifriendclient.repositories

import com.taxi.friend.taxifriendclient.models.Location

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserRepository {

    @PUT("users/{userId}/location")
    fun updateLocation(@Path("userId") driverId: String, @Body location: Location): Call<String>

}
