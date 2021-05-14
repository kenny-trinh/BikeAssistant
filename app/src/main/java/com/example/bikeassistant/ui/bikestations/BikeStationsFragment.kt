package com.example.bikeassistant.ui.bikestations

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bikeassistant.R
import com.example.bikeassistant.data.Contract
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_bike_stations.*
import okhttp3.*
import timber.log.Timber
import java.io.IOException

class BikeStationsFragment : Fragment() {

    var bikeStations = ArrayList<Contract.BikeStation>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView_bike_stations.apply {
            layoutManager = LinearLayoutManager(activity)

            // todo: extract strings to strings.xml
            var urls = getAllUrls(listOf("dublin"))
            fetchAllJson(urls)

            button_dublin.setOnClickListener {
                var urls = getAllUrls(listOf("dublin"))
                fetchAllJson(urls)
            }

            button_paris.setOnClickListener {
                var urls = getAllUrls(listOf("cergy-pontoise", "creteil"))
                fetchAllJson(urls)
            }
            //var contractDublin = listOf("dublin")
            //var contractParis = listOf("cergy-pontoise", "creteil")
            //var allContractNames = contractDublin + contractParis
            //var urls = getAllUrls(allContractNames)

        }
    }

    private fun getAllUrls(contractNames: List<String>): MutableList<String> {
        var apiKey = "d698bd9f3088ba5f431482fafabc3abd5199ead4"
        var urls = mutableListOf<String>()
        for (contractName in contractNames) {
            // example of dublin bike stations: https://api.jcdecaux.com/vls/v1/stations?contract=dublin&apiKey=d698bd9f3088ba5f431482fafabc3abd5199ead4
            var url = "https://api.jcdecaux.com/vls/v1/stations?contract=" + contractName + "&apiKey=" + apiKey
            urls.add(url)
        }
        return urls
    }

    // todo: separate bike stations into cities (dublin has a single url, paris has two urls)
    // todo: refactor nested lines function



    private fun fetchAllJson(urls: List<String>) {
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

                    if (url == urls[urls.lastIndex]) {
                        bikeStations.sortBy { bikeStation -> bikeStation.name }
                        runOnUiThread {
                            recyclerView_bike_stations.adapter = BikeStationsAdapter(bikeStations)
                        }
                    }

                    Timber.i("JSON data successfully loaded")
                }

            })
        }
    }

    // to run on main thread
    private fun runOnUiThread(task: Runnable) {
        Handler(Looper.getMainLooper()).post(task)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_bike_stations, container, false)
    }

}