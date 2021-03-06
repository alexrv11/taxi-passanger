package com.taxi.friend.taxifriendclient.models


import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)

class ResponseWrapper<T>(var errors: ErrorMessage? = null, var result: T? = null)
