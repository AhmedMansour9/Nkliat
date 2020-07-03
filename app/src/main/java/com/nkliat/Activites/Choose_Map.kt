package com.nkliat.Activites

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.nkliat.Model.MessageEvent
import com.nkliat.R
import com.tayser.utils.CustomToast
import kotlinx.android.synthetic.main.activity_choose__map.*
import org.greenrobot.eventbus.EventBus
import java.io.IOException
import java.util.*

class Choose_Map : AppCompatActivity() , OnMapReadyCallback,
com.google.android.gms.location.LocationListener,
GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    var googleMap: GoogleMap? = null
    var locationReques: LocationRequest? = null
    var mGoogleApiClient: GoogleApiClient? = null
    var auto: AutoCompleteTextView? = null
    var view: View? = null
    var MY_PERMISSIONS_REQUEST_LOCATION = 99
    val REQUEST_LOCATION_CODE = 99
    var address: String? = String()
    private val GOOGLE_PLACES_API_KEY = "AIzaSyAK7mgLlaOuCMfVwUE5gxgbF_fQpdL8pFc"
    private val AUTOCOMPLETE_REQUEST_CODE = 123
    lateinit var m: Marker
    var lat:Double=0.0
    var lng:Double=0.0
     var type:String?= String()
    var bundle:Bundle?= Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose__map)
        Places.initialize(this, GOOGLE_PLACES_API_KEY);

        var mapFragment = supportFragmentManager?.findFragmentById(R.id.MAP) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        type=intent.getStringExtra("type")
        checkLocationPermission()
        E_SearchPlace.setOnClickListener() {
            createAutoCompleteIntent()
        }

        Btn_Save.setOnClickListener(){
            if(type.equals("from_furntiture")){
                if(!address.isNullOrEmpty()){
                    EventBus.getDefault().postSticky(MessageEvent("from_furntiture",lat.toString(),
                        lng.toString(),"","",address!!))
                    finish()
                }else {
                    CustomToast.toastIconError(resources.getString(R.string.add_address),this)
                }
            }
            if(type.equals("to_furntiture")){
                if(!address.isNullOrEmpty()){
                    EventBus.getDefault().postSticky(MessageEvent("to_furntiture","",
                        "",lat.toString(),lng.toString(),address!!))
                    finish()
                }else {
                    CustomToast.toastIconError(resources.getString(R.string.add_address),this)
                }
            }
            if(type.equals("from_trailer")){
                        if(!address.isNullOrEmpty()){
                            EventBus.getDefault().postSticky(MessageEvent("from_trailer",lat.toString(),
                                lng.toString(),"","",address!!))
                            finish()
                        }else {
                            CustomToast.toastIconError(resources.getString(R.string.add_address),this)
                        }
                    }
            if(type.equals("to_trailer")){
                if(!address.isNullOrEmpty()){
                    EventBus.getDefault().postSticky(MessageEvent("to_trailer","",
                        "",lat.toString(),lng.toString(),address!!))
                    finish()
                }else {
                    CustomToast.toastIconError(resources.getString(R.string.add_address),this)
                }
            }
        }

    }

    private fun createAutoCompleteIntent() {
        val fields: List<Place.Field> =
            Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
        val intent: Intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.FULLSCREEN, fields
        )
            .build(this)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onMapReady(googleMaps: GoogleMap?) {
        googleMap = googleMaps
        buildGoogleapiclint()
    }

    override fun onLocationChanged(p0: Location?) {

    }

    @Synchronized
    private fun buildGoogleapiclint() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .enableAutoManage(this, this)
            .build()
        mGoogleApiClient!!.connect()

    }


    fun checkLocationPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
            false
        } else {
            true
        }
    }

    override fun onConnected(p0: Bundle?) {
        locationReques = LocationRequest()
        locationReques!!.smallestDisplacement = 1f
        locationReques!!.interval = 1000
        locationReques!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val permissionCheck = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                locationReques,
                this
            )
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationReques!!)
            val client = LocationServices.getSettingsClient(this)
            val task =
                client.checkLocationSettings(builder.build())
            task.addOnSuccessListener(
                this,
                OnSuccessListener<LocationSettingsResponse?> { })
            task.addOnFailureListener(this,
                OnFailureListener { e ->
                    if (e is ResolvableApiException) {
                        try {
                            val resolvable = e as ResolvableApiException
                            resolvable.startResolutionForResult(
                                this,
                                REQUEST_LOCATION_CODE
                            )
                        } catch (sendEx: SendIntentException) {
                        }
                    }
                })
        }
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            AUTOCOMPLETE_REQUEST_CODE -> when (resultCode) {
                Activity.RESULT_OK -> {
                    val place =
                        Autocomplete.getPlaceFromIntent(
                            data!!
                        )
                    Log.i("tag", "Place: " + place.name + ", " + place.latLng)
//                    tvLocationName.setText(place.name)
                    try {
                        val currentPlace = CameraPosition.Builder()
                            .target(place.latLng)
                            .bearing(240f).tilt(30f).zoom(16f).build()
                        googleMap!!.animateCamera(
                            CameraUpdateFactory.newCameraPosition(
                                currentPlace
                            )
                        )
                        E_SearchPlace.text = place.address
                        if(::m.isInitialized){
                          m.remove()
                        }
                        val markerOptions = MarkerOptions()
                        place.latLng?.let {
                            markerOptions.position(it).title(place.address)
                                .draggable(true)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                        }
                        lat=place.latLng!!.latitude
                        lng=place.latLng!!.longitude
                        address=place.address
                        m= googleMap!!.addMarker(markerOptions)

                        configureCameraIdle()


                    } catch (e: NullPointerException) {
                    }

                }
                Activity.RESULT_CANCELED -> {
                }
                else -> {
                }
            }
        }

    }

    private fun configureCameraIdle() {
        googleMap!!.setOnCameraIdleListener {
            val latLng: LatLng = googleMap!!.getCameraPosition().target


            val geocoder = Geocoder(this)
            try {
                val addressList =
                    geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                if (addressList != null && addressList.size > 0) {
                    val locality = addressList[0].getAddressLine(0)
                    val country = addressList[0].countryName
//                    if (!locality.isEmpty() && !country.isEmpty()) resutText.setText("$locality  $country")
                        m.remove()
                        val markerOptions = MarkerOptions()
                        markerOptions.position(latLng).title(locality)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                    lat=latLng.latitude
                    lng=latLng.longitude
                    address=locality
                    m=googleMap!!.addMarker(markerOptions)

                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}
