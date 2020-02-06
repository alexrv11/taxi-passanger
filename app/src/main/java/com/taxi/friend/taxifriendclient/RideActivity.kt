package com.taxi.friend.taxifriendclient

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.ClusterManager.OnClusterClickListener
import com.google.maps.android.clustering.ClusterManager.OnClusterItemClickListener
import com.taxi.friend.taxifriendclient.models.ClusterDriver
import com.taxi.friend.taxifriendclient.models.DriverLocation
import com.taxi.friend.taxifriendclient.models.ResponseWrapper
import com.taxi.friend.taxifriendclient.services.DriverService
import com.taxi.friend.taxifriendclient.utils.ClusterDriverManagerRenderer
import com.taxi.friend.taxifriendclient.utils.GPSCoordinate
import com.taxi.friend.taxifriendclient.utils.LatLngInterpolator
import com.taxi.friend.taxifriendclient.utils.MarkerAnimation

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RideActivity : FragmentActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,  OnClusterItemClickListener<ClusterDriver>, OnClusterClickListener<ClusterDriver> {


    private var map: GoogleMap? = null
    private var googleApiClient: GoogleApiClient? = null
    private var lastMarker: Marker? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    private var locationCallback: LocationCallback? = null
    private var locationRequest: LocationRequest? = null

    private var clusterManager: ClusterManager<ClusterDriver>? = null
    private var driverManagerRenderer: ClusterDriverManagerRenderer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment!!.getMapAsync(this)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult == null) {
                    return
                }

                for (location in locationResult.locations) {
                    if (lastLocation == null) {
                        lastLocation = location
                        PassangerInfo.location = com.taxi.friend.taxifriendclient.models.Location(location.latitude, location.longitude)
                    }

                    if (location != null) {
                        val lat2 = location.latitude
                        val lon2 = location.longitude
                        val lat1 = lastLocation!!.latitude
                        val lon1 = lastLocation!!.longitude
                        val diff = GPSCoordinate.distanceInMeterBetweenEarthCoordinates(lat2, lon2, lat1, lon1)
                        if (diff > MIN_DISTANCE) {

                            lastLocation = location
                            PassangerInfo.location = com.taxi.friend.taxifriendclient.models.Location(location.latitude, location.longitude)
                            displayLocation()
                            //updateDriverLocation()

                        }
                    }

                }
            }
        }


        startLocationUpdates()

        setUpLocation()
        displayLocation()
        //displayDrivers()

    }

    override fun onClusterClick(p0: Cluster<ClusterDriver>?): Boolean {
        Log.i("TaxiClick", "click event")

        return true
    }

    override fun onClusterItemClick(driver: ClusterDriver?): Boolean {
        Log.i("TaxiClick", "click event")
        val intent = Intent(this, DriverActivity::class.java)
        intent.putExtra("driverId", driver!!.driver!!.id)
        startActivity(intent)

        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSION_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                buildGoogleApiclient()
                createLocationRequest()
                displayLocation()
                //displayDrivers()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(this.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        map = googleMap
        map!!.isMyLocationEnabled = true
        clusterManager = ClusterManager(this, map)
        map.let { node -> node?.setOnMarkerClickListener(clusterManager)}

        driverManagerRenderer = ClusterDriverManagerRenderer(this, map!!, clusterManager!!)
        clusterManager!!.renderer = driverManagerRenderer
        clusterManager!!.setOnClusterItemClickListener(this)
        clusterManager!!.setOnClusterClickListener(this)

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = map!!.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle))

            if (!success) {
                Log.e("ErrorFailLoadMapStyle", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("ErrorNotFoundResource", "Can't find style. Error: ", e)
        }


        displayLocation()
        //displayDrivers()
    }

    private fun startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val locationRequest = LocationRequest.create()
                .setInterval(5000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        mFusedLocationClient!!.requestLocationUpdates(locationRequest,
                locationCallback!!, null)

    }

    private fun setUpLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSION_REQUEST_CODE)
        } else {
            buildGoogleApiclient()
            createLocationRequest()
            displayLocation()
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest!!.interval = UPDATE_INTERVAL.toLong()
        locationRequest!!.interval = FASTEST_INTERVAL.toLong()
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest!!.smallestDisplacement = DISPLACEMENT.toFloat()

    }

    private fun displayLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        if (lastLocation != null) {

            showMarkerPosition(lastLocation!!)

        } else {
            Log.d("ERROR", "Cannot get your location")
        }
    }

    private fun showMarkerPosition(currentLocation: Location) {

        if (lastMarker != null) {
            lastMarker!!.remove()
        }

        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f))
        if (lastMarker == null)
            lastMarker = map!!.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker()).position(latLng))
        else
            MarkerAnimation.animateMarkerToGB(lastMarker!!, latLng, LatLngInterpolator.Spherical())
    }

    private fun buildGoogleApiclient() {
        googleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        googleApiClient!!.connect()
    }

    override fun onConnected(bundle: Bundle?) {
        displayLocation()
        startLocationUpdates()
    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    override fun onLocationChanged(location: Location) {
        lastLocation = location
        PassangerInfo.location = com.taxi.friend.taxifriendclient.models.Location(location.latitude, location.longitude)
        displayLocation()
        //displayDrivers()
    }

    override fun onStop() {
        super.onStop()
        if (mFusedLocationClient != null)
            mFusedLocationClient!!.removeLocationUpdates(locationCallback!!)
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        mFusedLocationClient!!.removeLocationUpdates(locationCallback!!)
    }

    private fun displayDrivers() {
        try {
            val service = DriverService()
            val callDrivers = service.getDrivers(2.0, -34.634891, -58.439053)

            callDrivers.enqueue(object : Callback<ResponseWrapper<List<DriverLocation>>> {
                override fun onResponse(call: Call<ResponseWrapper<List<DriverLocation>>>, response: Response<ResponseWrapper<List<DriverLocation>>>) {
                    val drivers = response.body()!!.result
                    for (i in drivers!!.indices) {
                        val driver = drivers[i]
                        val latitude = driver.latitude
                        val longitude = driver.longitude
                        Log.i("taxiitem", "taxi item" + driver.id)

                        /*val driverMarker = ClusterDriver(LatLng(latitude, longitude),
                                driver.name, "", R.mipmap.ic_taxi_free, driver)

                        clusterManager!!.addItem(driverMarker)*/
                    }

                    clusterManager!!.cluster()
                }

                override fun onFailure(call: Call<ResponseWrapper<List<DriverLocation>>>, t: Throwable) {
                    Log.e("ErrorGettingDrivers", t.message)
                }
            })

        } catch (e: Exception) {
            Log.i("taxi", "taxi error")
        }

    }

    companion object {

        private val MY_PERMISSION_REQUEST_CODE = 7000

        private val UPDATE_INTERVAL = 5000
        private val FASTEST_INTERVAL = 3000
        private val DISPLACEMENT = 10
        private val MIN_DISTANCE = 8
    }

/*
    private void updateDriverLocation(){
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
