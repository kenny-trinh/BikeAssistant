package com.example.bikeassistant.ui.bikestations

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bikeassistant.R
import timber.log.Timber

class BikeStationDetailActivity : AppCompatActivity() {

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
    }
}