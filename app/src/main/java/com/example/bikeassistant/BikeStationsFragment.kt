package com.example.bikeassistant

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_bike_stations.*
import okhttp3.*
import timber.log.Timber
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BikeStationsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BikeStationsFragment : Fragment() {

    var bikeStations = ArrayList<BikeStation>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView_bike_stations.apply {
            layoutManager = LinearLayoutManager(activity)

            // todo: extract strings to strings.xml


            var contractNames = listOf("dublin", "cergy-pontoise", "creteil")
            var apiKey = "d698bd9f3088ba5f431482fafabc3abd5199ead4"
            var urls = mutableListOf<String>()
            for (contractName in contractNames) {
                // example of dublin bike stations: https://api.jcdecaux.com/vls/v1/stations?contract=dublin&apiKey=d698bd9f3088ba5f431482fafabc3abd5199ead4
                var url = "https://api.jcdecaux.com/vls/v1/stations?contract=" + contractName + "&apiKey=" + apiKey
                urls.add(url)
            }
            fetchAllJson(urls)
        }
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
                    val type = object : TypeToken<ArrayList<BikeStation>>() {}.type
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