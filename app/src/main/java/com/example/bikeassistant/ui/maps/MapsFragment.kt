package com.example.bikeassistant.ui.maps

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bikeassistant.R
import com.example.bikeassistant.data.Contract
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_maps.*
import okhttp3.*
import timber.log.Timber
import java.io.IOException

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    var contractDublin = listOf("dublin")
    var contractParis = listOf("cergy-pontoise", "creteil")
    var allContractNames = contractDublin + contractParis
    var bikeStations = ArrayList<Contract.BikeStation>()
    var latLng = LatLng(53.349562, -6.278198)
    var zoomLevel = 12f

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        getInitialMap()
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInitialMap()
        button_map_dublin.setOnClickListener {
            latLng = LatLng(53.3498, -6.2603)
            zoomLevel = 12f
            getAllMarkers(getAllUrls(allContractNames))
        }

        button_map_paris.setOnClickListener {
            latLng = LatLng(48.8566, 2.3522)
            zoomLevel = 10f
            getAllMarkers(getAllUrls(allContractNames))
        }
    }

    private fun getInitialMap() {
        latLng = LatLng(53.3498, -6.2603)
        zoomLevel = 12f
        getAllMarkers(getAllUrls(allContractNames))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getInitialMap()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getInitialMap()
    }

    override fun onStart() {
        super.onStart()
        getInitialMap()
    }

    override fun onResume() {
        super.onResume()
        getInitialMap()
    }

    private fun getMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        runOnUiThread {
            mapFragment.getMapAsync(this)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
        for (bikeStation in bikeStations) {
            map.addMarker(MarkerOptions()
                    .position(LatLng(bikeStation.position.lat, bikeStation.position.lng))
                    .title(bikeStation.name))
        }
    }

    private fun getAllUrls(contractNames: List<String>): MutableList<String> {
        var apiKey = "d698bd9f3088ba5f431482fafabc3abd5199ead4"
        var urls = mutableListOf<String>()
        for (contractName in contractNames) {
            // example of dublin bike stations: https://api.jcdecaux.com/vls/v1/stations?contract=dublin&apiKey=d698bd9f3088ba5f431482fafabc3abd5199ead4
            var url = "https://api.jcdecaux.com/vls/v1/stations?contract=$contractName&apiKey=$apiKey"
            urls.add(url)
        }
        return urls
    }

    // todo: separate bike stations into cities (dublin has a single url, paris has two urls)
    // todo: refactor nested lines function
    private fun getAllMarkers(urls: List<String>) {
        Timber.i("Fetching json data from API")
        for (url in urls) {
            var request = Request.Builder().url(url).build()
            var client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Timber.i("Failed to load JSON data")
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response?.body?.string()
                    val gson = GsonBuilder().create()
                    val type = object : TypeToken<ArrayList<Contract.BikeStation>>() {}.type
                    bikeStations = ArrayList()
                    bikeStations.addAll(gson.fromJson(body, type))
                    Timber.i("JSON data successfully loaded")
                    getMap()
                }

            })
        }
    }

    // to run on main thread
    private fun runOnUiThread(task: Runnable) {
        Handler(Looper.getMainLooper()).post(task)
    }
}