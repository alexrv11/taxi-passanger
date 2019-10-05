package com.taxi.friend.taxifriendclient.utils

import android.util.Log


object GPSCoordinate {

    val RADIO_EART = 6371

    fun distanceInMeterBetweenEarthCoordinates(lat1: Double, long1: Double, lat2: Double, long2: Double): Double {
        var lat1 = lat1
        var lat2 = lat2

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(long2 - long1)

        lat1 = Math.toRadians(lat1)
        lat2 = Math.toRadians(lat2)

        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val result = RADIO_EART * c * 1000
        Log.i("DistanceDriver", String.format("Distance driver %f", result))
        return result
    }
}

