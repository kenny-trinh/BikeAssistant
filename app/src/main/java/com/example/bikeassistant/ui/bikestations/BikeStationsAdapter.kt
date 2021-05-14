package com.example.bikeassistant.ui.bikestations

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bikeassistant.R
import com.example.bikeassistant.data.Contract
import kotlinx.android.synthetic.main.bike_station_row.view.*

class BikeStationsAdapter(val bikeStations: ArrayList<Contract.BikeStation>) : RecyclerView.Adapter<BikeStationsViewHolder>() {
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
        holder.itemView.textView_bike_station_contract?.text = bikeStation.contractName
        holder.itemView.textView_available_bike_stands?.text = bikeStation.availableBikeStands.toString()
        holder.itemView.textView_available_bikes?.text = bikeStation.availableBikes.toString()
        holder.itemView.textView_bike_station_status?.text = bikeStation.status
    }
}

class BikeStationsViewHolder(val view: View, var bikeStation: Contract.BikeStation? = null, var id: Int? = null) : RecyclerView.ViewHolder(view) {

    companion object {
        val BIKESTATION_NAME_KEY = "BIKESTATION_NAME"
        val BIKESTATION_ID_KEY = "BIKESTATION_ID"
    }

    init {
        view.setOnClickListener {
            val intent = Intent(view.context, BikeStationDetailFragment::class.java)
            intent.putExtra(BIKESTATION_NAME_KEY, bikeStation?.name)
            intent.putExtra(BIKESTATION_ID_KEY, id)
            view.context.startActivity(intent)
        }
    }

}
