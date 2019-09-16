package com.taxi.friend.taxifriendclient.models;


public class DriverData {
    private String name;
    private String identityCar;

    public DriverData(String name, String identityCar) {
        this.name = name;
        this.identityCar = identityCar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityCar() {
        return identityCar;
    }

    public void setIdentityCar(String identityCar) {
        this.identityCar = identityCar;
    }
}
