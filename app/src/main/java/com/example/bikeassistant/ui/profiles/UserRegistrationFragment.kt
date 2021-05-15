package com.example.bikeassistant.ui.profiles

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.bikeassistant.R
import com.example.bikeassistant.data.Profile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_user_registration.*
import timber.log.Timber


class UserRegistrationFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("Timber: UserRegistrationFragment - onViewCreated called")
        var profiles: ArrayList<Profile> = ArrayList()
        var navigationController: NavController?
        navigationController = Navigation.findNavController(view)

        button_register.setOnClickListener { view ->
            var firstName = editText_first_name.text.toString()
            var lastName = editText_last_name.text.toString()
            var email = editText_email.text.toString()
            profiles = loadProfilesFromSharedPrefs()
            profiles.add(Profile(firstName, lastName, email))
            val gson = Gson()
            val prefs = requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            val json = gson.toJson(profiles)
            editor.putString("profiles", json)
            editor.apply()
            navigationController.navigate(R.id.profilesFragment)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("Timber: UserRegistrationFragment - onCreateView called")
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_user_registration, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.navdrawer_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    private fun loadProfilesFromSharedPrefs(): ArrayList<Profile> {
        val gson = Gson()
        val prefs = requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE)

        var profiles: ArrayList<Profile> = arrayListOf()

        if (prefs.contains("profiles")){
            val json = prefs.getString("profiles",null)
            val type = object : TypeToken<ArrayList<Profile>>() {}.type
            profiles = gson.fromJson(json, type)
        }
        return profiles
    }


}