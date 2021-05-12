package com.example.bikeassistant.data

data class BikeStation(val contractName: String, val name: String, val address: String, val lat: Double, val lng: Double, val bikeStands: Int, val availableBikeStands: Int, val availableBikes: Int, val status: String, val isFavorite: Boolean)