package com.taxi.friend.taxifriendclient.repositories

import com.taxi.friend.taxifriendclient.models.InputOrder
import com.taxi.friend.taxifriendclient.models.OrderStatus
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface OrderRepository {

    @POST("orders")
    fun createOrder(@Body order: InputOrder): Call<OrderStatus>


    @GET("orders/{orderId}")
    fun getOrderStatus(@Path("orderId") id: String): Call<OrderStatus>
}
