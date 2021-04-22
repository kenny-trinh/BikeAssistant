package com.example.bikeassistant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.profile_row.view.*

class ProfilesAdapter(val profiles: ArrayList<Profile>) : RecyclerView.Adapter<ProfilesViewHolder>() {
    override fun getItemCount(): Int {
        return profiles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.profile_row, parent, false)
        return ProfilesViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ProfilesViewHolder, position: Int) {
        val profile = profiles[position]
        holder.itemView.textView_full_name?.text = profile.firstName + " " + profile.lastName
    }
}

class ProfilesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}
