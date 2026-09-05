package com.example.taqreeb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_profile,
            container,
            false
        )

        val profileName = view.findViewById<TextView>(
            R.id.txtProfileName
        )

        val profileEmail = view.findViewById<TextView>(
            R.id.txtProfileEmail
        )

        val editProfileButton = view.findViewById<Button>(
            R.id.btnEditProfile
        )

        val logoutButton = view.findViewById<Button>(
            R.id.btnLogout
        )

        // Get saved user information
        val preferences = requireContext().getSharedPreferences(
            "TaqreebData",
            Context.MODE_PRIVATE
        )

        val savedName = preferences.getString(
            "name",
            "User"
        )

        val savedEmail = preferences.getString(
            "email",
            "No email"
        )

        // Display user information
        profileName.text = savedName
        profileEmail.text = savedEmail


        editProfileButton.setOnClickListener {

            val intent = Intent(
                requireContext(),
                EditProfileActivity::class.java
            )

            startActivity(intent)
        }


        // Logout
        logoutButton.setOnClickListener {

            val intent = Intent(
                requireContext(),
                LoginActivity::class.java
            )

            startActivity(intent)

            requireActivity().finish()
        }

        return view
    }
}