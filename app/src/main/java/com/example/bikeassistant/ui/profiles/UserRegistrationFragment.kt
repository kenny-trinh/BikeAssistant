package com.example.bikeassistant.ui.profiles

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bikeassistant.R
import com.example.bikeassistant.data.Contract
import com.example.bikeassistant.data.Profile
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_user_registration.*
import android.preference.PreferenceManager


class UserRegistrationFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var profiles: ArrayList<Profile> = arrayListOf()


        button_register.setOnClickListener { view ->
            var firstName = editText_first_name.text.toString()
            var lastName = editText_last_name.text.toString()
            var email = editText_email.text.toString()
            profiles.add(Profile(firstName, lastName, email))
            val gson = Gson()
            val prefs = requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            val json = gson.toJson(profiles)
            editor.putString("profiles", json)
            editor.apply()

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_registration, container, false)

    }


}