package com.taxi.friend.taxifriendclient.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class ResponseWrapper<T> {

    private ErrorMessage errors;
    private T result;

    public ResponseWrapper(ErrorMessage errors, T result) {
        this.errors = errors;
        this.result = result;
    }

    public ErrorMessage getErrors() {
        return errors;
    }

    public void setErrors(ErrorMessage errors) {
        this.errors = errors;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
