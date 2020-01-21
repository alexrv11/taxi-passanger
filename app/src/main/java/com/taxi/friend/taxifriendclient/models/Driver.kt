package com.taxi.friend.taxifriendclient.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
class Driver(name: String = "", frontCarPhoto: String = "", backCarPhoto: String = "",
             sideCarPhoto: String = "", phone: String = "", carIdentity: String = "") {
    val name = name

    @JsonProperty("front_car_photo")
    val frontCarPhoto = frontCarPhoto

    @JsonProperty("back_car_photo")
    val backCarPhoto = backCarPhoto


    @JsonProperty("side_car_photo")
    val sideCarPhoto = sideCarPhoto

    @JsonProperty("phone")
    val phone = phone

    @JsonProperty("car_identity")
    val carIdentity = carIdentity

}
