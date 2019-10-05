package com.taxi.friend.taxifriendclient.repositories

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RestClient {

    fun createRestClient(): Retrofit {

        return Retrofit.Builder()
                .baseUrl("http://192.168.0.9:1323/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
    }

}
