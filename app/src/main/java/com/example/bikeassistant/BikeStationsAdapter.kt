package com.example.bikeassistant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bike_station_row.view.*

class BikeStationsAdapter(val bikeStations: ArrayList<BikeStation>) : RecyclerView.Adapter<BikeStationsViewHolder>() {
    override fun getItemCount(): Int {
        return bikeStations.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BikeStationsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.bike_station_row, parent, false)
        return BikeStationsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: BikeStationsViewHolder, position: Int) {
        val bikeStation = bikeStations[position]
        holder.itemView.textView_bike_station_name?.text = bikeStation.name
    }
}

class BikeStationsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}
