package com.taxi.friend.taxifriendclient.utils

import android.util.Log
import kotlin.math.atan2
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.cos


object GPSCoordinate {

    val RADIO_EART = 6371

    fun distanceInMeterBetweenEarthCoordinates(lat1: Double, long1: Double, lat2: Double, long2: Double): Double {


        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(long2 - long1)

        var latitude = Math.toRadians(lat1)
        var latitude2 = Math.toRadians(lat2)

        val a = sin(dLat / 2) * sin(dLat / 2) + sin(dLon / 2) * sin(dLon / 2) * cos(latitude) * cos(latitude2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val result = RADIO_EART * c * 1000
        Log.i("DistanceDriver", String.format("Distance driver %f", result))
        return result
    }
}

