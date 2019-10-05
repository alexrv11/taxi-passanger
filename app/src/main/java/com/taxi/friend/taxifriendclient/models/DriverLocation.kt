package com.taxi.friend.taxifriendclient.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class DriverLocation(var id: String?, var name: String?, var latitude: Double?, var longitude: Double, var status: String?, var direction: Int)
