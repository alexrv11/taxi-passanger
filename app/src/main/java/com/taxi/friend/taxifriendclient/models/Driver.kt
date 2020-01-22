package com.taxi.friend.taxifriendclient.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
class Driver(id: String = "", name: String = "", frontCarPhoto: String = "", backCarPhoto: String = "",
             sideCarPhoto: String = "", phone: String = "", carIdentity: String = "") {

    val id = id
    val name = name
    val frontCarPhoto = frontCarPhoto
    val backCarPhoto = backCarPhoto
    val sideCarPhoto = sideCarPhoto
    val phone = phone
    val carIdentity = carIdentity

}
