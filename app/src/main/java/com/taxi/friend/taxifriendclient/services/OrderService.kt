package com.taxi.friend.taxifriendclient.services

import com.taxi.friend.taxifriendclient.models.InputOrder
import com.taxi.friend.taxifriendclient.models.Location
import com.taxi.friend.taxifriendclient.models.OrderStatus
import com.taxi.friend.taxifriendclient.repositories.OrderRepository
import com.taxi.friend.taxifriendclient.repositories.RestClient
import retrofit2.Call

class OrderService {

     @Throws(Exception::class)
     fun createOrder(driverId: String, location: Location): Call<OrderStatus> {
        val service = RestClient.createRestClient().create(OrderRepository::class.java)


        val order = InputOrder(driverId, location.latitude, location.longitude)
        return service.createOrder(order)
     }

    @Throws(Exception::class)
    fun getOrderStatus(orderId: String): Call<OrderStatus> {
        val service = RestClient.createRestClient().create(OrderRepository::class.java)

        return service.getOrderStatus(orderId)
    }
 }
