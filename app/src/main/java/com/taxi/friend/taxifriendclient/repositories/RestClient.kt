package com.taxi.friend.taxifriendclient.repositories

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RestClient {

    fun createRestClient(): Retrofit {

        return Retrofit.Builder()
                .baseUrl("https://igyglwsme3.execute-api.us-east-1.amazonaws.com/prod/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
    }

}
