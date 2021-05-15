package com.example.bikeassistant.ui.profiles

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bikeassistant.R
import com.example.bikeassistant.data.Profile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_profiles.*
import timber.log.Timber


class ProfilesFragment : Fragment() {

    var profiles: ArrayList<Profile> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.i("Timber: ProfilesFragment - onViewCreated called")

        val gson = Gson()
        val prefs = requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE)

        if (prefs.contains("profiles")){
            val json = prefs.getString("profiles",null)
            val type = object : TypeToken<ArrayList<Profile>>() {}.type
            profiles = gson.fromJson(json, type)
            val mainAdapter = ProfilesAdapter(profiles)
            recyclerView_profiles.layoutManager = LinearLayoutManager(activity)
            recyclerView_profiles.adapter = mainAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Timber.i("Timber: ProfilesFragment - onCreateView called")
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_profiles, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.navdrawer_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }


}