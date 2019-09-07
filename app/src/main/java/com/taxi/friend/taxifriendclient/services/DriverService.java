package com.taxi.friend.taxifriendclient.services;


import com.taxi.friend.taxifriendclient.models.DriverLocation;
import com.taxi.friend.taxifriendclient.models.ResponseWrapper;
import com.taxi.friend.taxifriendclient.repositories.DriverRepository;
import com.taxi.friend.taxifriendclient.repositories.RestClient;

import java.util.List;

import retrofit2.Call;

public class DriverService {



    public Call<ResponseWrapper<List<DriverLocation>>> getDrivers(double radio, double latitude, double longitude) throws Exception {

        DriverRepository service = RestClient.createRestClient().create(DriverRepository.class);
        Call<ResponseWrapper<List<DriverLocation>>> result = service.getDrivers(radio, latitude, longitude);
        return result;
    }

    /*
    public Call<String> createOrder(String driverId, Location location){
        DriverRepository service = RestClient.createRestClient().create(DriverRepository.class);

        return service.updateLocation(driverId, new com.taxi.friend.drivers.models.Location(location.getLatitude(), location.getLongitude()));
    }*/

/*
    public void getDriver(String driverId){
        DriverRepository repository = RestClient.createRestClient().create(DriverRepository.class);

        Call<ResponseWrapper<DriverInfor>> callUser = repository.getDriver(driverId);
        callUser.enqueue(new Callback<ResponseWrapper<User>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<User>> call, Response<ResponseWrapper<User>> response) {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    User user = response.body().getResult();
                    TaxiGlobalInfo.mainViewModel.getUser().setValue(user);
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<User>> call, Throwable t) {
                Log.e("ErrorDriveInfo", t.getMessage(), t);
            }
        });
        return ;
    }*/
}
