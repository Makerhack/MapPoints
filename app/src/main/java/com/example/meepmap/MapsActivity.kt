package com.example.meepmap

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.meepmap.model.Resource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val viewModel: MapsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLoadedCallback {
            val bounds = LatLngBounds(Constants.LOWER_LEFT, Constants.UPPER_RIGHT)
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 8))
        }
        mMap.setOnCameraIdleListener {
            mMap.clear()
            val bounds = mMap.projection.visibleRegion
            viewModel.updateResources(bounds.nearLeft, bounds.farRight)
        }

        viewModel.loadResources()?.observe(this, Observer {
            populateMap(it)
        })
    }

    private fun populateMap(resources: List<Resource?>?) {
        resources?.forEach {
            Log.d("MapsActivity", "Resource: ${it}")
            it?.apply {
                val marker: MarkerOptions = MarkerOptions()
                marker.position(LatLng(it.lat, it.lon))
                marker.title(it.name)
                marker.icon(BitmapDescriptorFactory.defaultMarker(getColorForZoneId(it.companyZoneId)))
                mMap.addMarker(marker)
            }
        }
    }

    fun getColorForZoneId(zoneId: Int): Float{
        BitmapDescriptorFactory.HUE_AZURE
        return when(zoneId){
            Constants.ZONE_ID_BLUE -> BitmapDescriptorFactory.HUE_BLUE
            Constants.ZONE_ID_GREEN -> BitmapDescriptorFactory.HUE_GREEN
            Constants.ZONE_ID_PURPLE -> BitmapDescriptorFactory.HUE_MAGENTA
            else  -> BitmapDescriptorFactory.HUE_RED
        }
    }
}
