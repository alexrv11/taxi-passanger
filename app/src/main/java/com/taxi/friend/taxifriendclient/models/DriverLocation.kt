package com.taxi.friend.taxifriendclient.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
class DriverLocation( id: String = "", name: String = "",  latitude: Double = 0.0, longitude: Double = 0.0, status: String = "", direction: Int = 0) {
    val id = id
    val name = name
    val latitude = latitude
    val longitude = longitude
    val status = status
    val direction = direction
}
