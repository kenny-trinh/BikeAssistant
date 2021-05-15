package com.example.bikeassistant.ui.bikestations

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bikeassistant.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_bike_station_detail.*
import timber.log.Timber

class BikeStationDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    var latLng = LatLng(53.349562, -6.278198)
    var zoomLevel = 15f
    var currentBikeStationName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("Timber: BikeStationDetailActivity onCreate called")
        setContentView(R.layout.activity_bike_station_detail)
        val bikeStationName = intent.getStringExtra(BikeStationsViewHolder.BIKESTATION_NAME_KEY)
        val contractName = intent.getStringExtra(BikeStationsViewHolder.BIKESTATION_CONTRACT_NAME_KEY)
        val address = intent.getStringExtra(BikeStationsViewHolder.BIKESTATION_ADDRESS_KEY)
        val availableBikeStands = intent.getStringExtra(BikeStationsViewHolder.BIKESTATION_AVAILABLE_BIKE_STANDS_KEY)
        val availableBikes = intent.getStringExtra(BikeStationsViewHolder.BIKESTATION_AVAILABLE_BIKES_KEY)
        val status = intent.getStringExtra(BikeStationsViewHolder.BIKESTATION_STATUS_KEY)
        val lat = intent.getDoubleExtra(BikeStationsViewHolder.BIKESTATION_POSITION_LAT_KEY, 0.0)
        val lng = intent.getDoubleExtra(BikeStationsViewHolder.BIKESTATION_POSITION_LNG_KEY, 0.0)

        var textBikeStationName = findViewById<TextView>(R.id.textView_bike_station_detail_name)
        textBikeStationName.text = bikeStationName
        supportActionBar?.title = bikeStationName
        var textContractName = findViewById<TextView>(R.id.textView_bike_station_detail_contractName)
        textContractName.text = contractName
        var textAddress = findViewById<TextView>(R.id.textView_bike_station_detail_address)
        textAddress.text = address
        var textAvailableBikeStands = findViewById<TextView>(R.id.textView_bike_station_detail_availableBikeStands)
        textAvailableBikeStands.text = availableBikeStands
        var textAvailableBikes = findViewById<TextView>(R.id.textView_bike_station_detail_availableBikes)
        textAvailableBikes.text = availableBikes
        var textStatus = findViewById<TextView>(R.id.textView_bike_station_detail_status)
        textStatus.text = status

        latLng = LatLng(lat, lng)
        currentBikeStationName = bikeStationName.toString()

        button_detail_map.setOnClickListener {
            setContentView(R.layout.fragment_maps)
            findViewById<Button>(R.id.button_map_dublin).visibility = View.GONE
            findViewById<Button>(R.id.button_map_paris).visibility = View.GONE
            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }

    }

    override fun onMapReady(googleMap: GoogleMap, ) {
        Timber.i("Timber: MapsFragment - onMapReady called")
        map = googleMap
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
        map.addMarker(
            MarkerOptions()
            .position(latLng)
            .title(currentBikeStationName))
    }
}