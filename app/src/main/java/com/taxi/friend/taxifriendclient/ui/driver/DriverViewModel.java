package com.taxi.friend.taxifriendclient.ui.driver;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taxi.friend.taxifriendclient.models.DriverData;

public class DriverViewModel extends ViewModel {
    private MutableLiveData<DriverData> driver;

    public LiveData<DriverData> getDriver(){

        if(driver == null) {
            driver = new MutableLiveData<>();
            driver.setValue(new DriverData("test name", "1234nkc"));
        }

        return driver;
    }
}
