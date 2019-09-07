package com.taxi.friend.taxifriendclient.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DriverLocation {
    private String id;
    private String name;
    private Double Latitude;
    private double longitude;
    private String status;
    private int direction;
}
