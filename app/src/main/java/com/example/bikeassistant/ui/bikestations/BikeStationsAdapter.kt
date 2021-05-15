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
        holder.bikeStation = bikeStation
        holder.itemView.textView_bike_station_name?.text = bikeStation.name
        holder.itemView.textView_bike_station_contract?.text = bikeStation.contractName[0].toUpperCase()+bikeStation.contractName.substring(1)
        holder.itemView.textView_available_bike_stands?.text = bikeStation.availableBikeStands.toString()
        holder.itemView.textView_available_bikes?.text = bikeStation.availableBikes.toString()
        holder.itemView.textView_bike_station_status?.text = bikeStation.status
    }
}

class BikeStationsViewHolder(val view: View, var bikeStation: Contract.BikeStation? = null,
                             var id: Int? = null) : RecyclerView.ViewHolder(view) {

    companion object {
        val BIKESTATION_NAME_KEY = "BIKESTATION_NAME"
//        val BIKESTATION_ID_KEY = "BIKESTATION_ID"
        val BIKESTATION_CONTRACT_NAME_KEY = "BIKESTATION_CONTRACT_NAME"
        val BIKESTATION_ADDRESS_KEY = "BIKESTATION_ADDRESS"
        val BIKESTATION_AVAILABLE_BIKE_STANDS_KEY = "BIKESTATION_AVAILABLE_BIKE_STANDS"
        val BIKESTATION_AVAILABLE_BIKES_KEY = "BIKESTATION_AVAILABLE_BIKES"
        val BIKESTATION_STATUS_KEY = "BIKESTATION_STATUS"
        val BIKESTATION_POSITION_LAT_KEY = "BIKESTATION_POSITION_LAT"
        val BIKESTATION_POSITION_LNG_KEY = "BIKESTATION_POSITION_LNG"
    }

    init {
        view.setOnClickListener {
            val intent = Intent(view.context, BikeStationDetailActivity::class.java)
            intent.putExtra(BIKESTATION_NAME_KEY, bikeStation?.name)
//            intent.putExtra(BIKESTATION_ID_KEY, id)
            intent.putExtra(BIKESTATION_CONTRACT_NAME_KEY, bikeStation?.contractName?.get(0)!!.toUpperCase()+ bikeStation!!.contractName.substring(1))
            intent.putExtra(BIKESTATION_ADDRESS_KEY, bikeStation?.address)
            intent.putExtra(BIKESTATION_AVAILABLE_BIKE_STANDS_KEY, bikeStation?.availableBikeStands.toString())
            intent.putExtra(BIKESTATION_AVAILABLE_BIKES_KEY, bikeStation?.availableBikes.toString())
            intent.putExtra(BIKESTATION_STATUS_KEY, bikeStation?.status)
            intent.putExtra(BIKESTATION_POSITION_LAT_KEY, bikeStation?.position?.lat)
            intent.putExtra(BIKESTATION_POSITION_LNG_KEY, bikeStation?.position?.lng)
            view.context.startActivity(intent)
        }
    }

}
