package com.taxi.friend.taxifriendclient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.taxi.friend.taxifriendclient.models.ClusterDriver;
import com.taxi.friend.taxifriendclient.models.DriverLocation;
import com.taxi.friend.taxifriendclient.models.ResponseWrapper;
import com.taxi.friend.taxifriendclient.services.DriverService;
import com.taxi.friend.taxifriendclient.utils.ClusterDriverManagerRenderer;
import com.taxi.friend.taxifriendclient.utils.GPSCoordinate;
import com.taxi.friend.taxifriendclient.utils.LatLngInterpolator;
import com.taxi.friend.taxifriendclient.utils.MarkerAnimation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    GoogleMap map;
    private GoogleApiClient googleApiClient;
    Marker lastMarker;
    FusedLocationProviderClient mFusedLocationClient;
    private Location lastLocation;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    private static final int MY_PERMISSION_REQUEST_CODE = 7000;

    private static int UPDATE_INTERVAL = 5000;
    private static int FASTEST_INTERVAL = 3000;
    private static int DISPLACEMENT = 10;
    private static int MIN_DISTANCE = 8;

    private ClusterManager<ClusterDriver> clusterManager;
    private ClusterDriverManagerRenderer driverManagerRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    if(lastLocation == null) {
                        lastLocation = location;
                    }

                    if(location != null){
                        double lat2 = location.getLatitude();
                        double lon2 = location.getLongitude();
                        double lat1 = lastLocation.getLatitude();
                        double lon1 = lastLocation.getLongitude();
                        if(GPSCoordinate.distanceInMeterBetweenEarthCoordinates(lat2, lon2, lat1, lon1) > MIN_DISTANCE){

                            lastLocation = location;

                            displayLocation();
                            //updateDriverLocation();

                        }
                    }

                }
            }
        };


        startLocationUpdates();

        setUpLocation();
        displayLocation();
        displayDrivers();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    buildGoogleApiclient();
                    createLocationRequest();
                    displayLocation();
                    displayDrivers();
                }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
         map = googleMap;
         map.setMyLocationEnabled(true);
         clusterManager = new ClusterManager<>(this.getApplicationContext(), map);
         driverManagerRenderer = new ClusterDriverManagerRenderer(this.getApplicationContext(),map, clusterManager);
         clusterManager.setRenderer(driverManagerRenderer);

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e("ErrorFailLoadMapStyle", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("ErrorNotFoundResource", "Can't find style. Error: ", e);
        }


        displayLocation();
        displayDrivers();
    }

    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(5000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        mFusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback, null);

    }

    private void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, MY_PERMISSION_REQUEST_CODE);
        } else {
            buildGoogleApiclient();
            createLocationRequest();
            displayLocation();
        }
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);

    }

    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (lastLocation != null) {

            showMarkerPosition(lastLocation);

        } else {
            Log.d("ERROR", "Cannot get your location");
        }
    }

    private void showMarkerPosition(@NonNull Location currentLocation) {

        if (lastMarker != null) {
            lastMarker.remove();
        }

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        map.animateCamera(CameraUpdateFactory.newLatLngZoom( latLng, 15.0f));
        if (lastMarker == null)
            lastMarker = map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker()).position(latLng));
        else
            MarkerAnimation.animateMarkerToGB(lastMarker, latLng, new LatLngInterpolator.Spherical());
    }

    private void buildGoogleApiclient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        displayLocation();
        displayDrivers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mFusedLocationClient != null)
            mFusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void displayDrivers(){
        try{
            DriverService service = new DriverService();
            Call<ResponseWrapper<List<DriverLocation>>> callDrivers = service.getDrivers(2,-34.634891,-58.439053);

            callDrivers.enqueue(new Callback<ResponseWrapper<List<DriverLocation>>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<List<DriverLocation>>> call, Response<ResponseWrapper<List<DriverLocation>>> response) {
                    List<DriverLocation> drivers = response.body().getResult();
                    for ( int i = 0; i < drivers.size(); i++){
                        DriverLocation driver = drivers.get(i);
                        double latitude = driver.getLatitude();
                        double longitude = driver.getLongitude();
                        Log.i("taxiitem", "taxi item" + driver.getId());

                        ClusterDriver driverMarker = new ClusterDriver(new LatLng(latitude, longitude),
                                driver.getName(),"", R.mipmap.ic_taxi_free, driver);

                        clusterManager.addItem(driverMarker);
                    }

                    clusterManager.cluster();
                }

                @Override
                public void onFailure(Call<ResponseWrapper<List<DriverLocation>>> call, Throwable t) {
                    Log.e("ErrorGettingDrivers", t.getMessage());
                }
            });

        }
        catch (Exception e){
            Log.i("taxi", "taxi error");
        }
    }


    /*private void updateDriverLocation(){
        try{
            DriverService service = new DriverService();
            Call<String> callUpdate = service.updateLocation(TaxiGlobalInfo.DriverId, lastLocation);

            callUpdate.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    int code = response.code();
                    if(code != HttpURLConnection.HTTP_OK){
                        Log.e("UpdateLocation", String.format("Response updating location: %d", code));
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("ErrorUpdateDriverLoc", t.getMessage());
                }
            });

        }
        catch (Exception e){
            Log.i("taxi", "taxi error");
        }
    }
*/

}
