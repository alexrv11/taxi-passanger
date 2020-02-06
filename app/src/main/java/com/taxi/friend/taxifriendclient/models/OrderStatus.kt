package com.taxi.friend.taxifriendclient.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
class OrderStatus(id: String = "", driverId: String = "", status: String = "") {
    val id = id

    @JsonProperty("driver_id")
    val driverId = driverId
    val status = status
}