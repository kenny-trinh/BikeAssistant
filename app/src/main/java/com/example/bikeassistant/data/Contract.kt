package com.example.bikeassistant.data

import com.google.gson.annotations.SerializedName

data class Contract(
    val bikeStations: ArrayList<BikeStation>
) {
    data class BikeStation(
        @SerializedName("contract_name")
        val contractName: String,
        val name: String,
        val address: String,
        val position: Position,
        @SerializedName("bike_stands")
        val bikeStands: Int,
        @SerializedName("available_bike_stands")
        val availableBikeStands: Int,
        @SerializedName("available_bikes")
        val availableBikes: Int,
        val status: String,
//        val isFavorite: Boolean
    ) {
        data class Position(
            val lat: Double,
            val lng: Double
        )
    }

}
