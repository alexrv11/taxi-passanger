package com.taxi.friend.taxifriendclient.services


import com.taxi.friend.taxifriendclient.models.Driver
import com.taxi.friend.taxifriendclient.models.DriverLocation
import com.taxi.friend.taxifriendclient.models.ResponseWrapper
import com.taxi.friend.taxifriendclient.repositories.DriverRepository
import com.taxi.friend.taxifriendclient.repositories.RestClient

import retrofit2.Call

class DriverService {


    @Throws(Exception::class)
    fun getDrivers(radio: Double, latitude: Double, longitude: Double): Call<ResponseWrapper<List<DriverLocation>>> {

        val service = RestClient.createRestClient().create(DriverRepository::class.java)
        return service.getDrivers(radio, latitude, longitude)
    }

    @Throws(Exception::class)
    fun getDriver(id: String): Call<Driver> {

        val service = RestClient.createRestClient().create(DriverRepository::class.java)
        return service.getDriver(id)
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
