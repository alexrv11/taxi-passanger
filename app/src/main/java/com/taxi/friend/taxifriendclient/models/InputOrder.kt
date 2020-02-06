package com.taxi.friend.taxifriendclient.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
class InputOrder(driverId: String = "", latitude: Double = 0.0, longitude: Double = 0.0) {
    @JsonProperty("driver_id")
    val driverId = driverId
    val latitude = latitude
    val longitude = longitude
}