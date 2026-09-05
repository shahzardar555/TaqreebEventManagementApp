package com.example.taqreeb

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView

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

        profileName = view.findViewById(
            R.id.txtProfileName
        )

        profileEmail = view.findViewById(
            R.id.txtProfileEmail
        )

        val editProfileButton = view.findViewById<Button>(
            R.id.btnEditProfile
        )

        val logoutButton = view.findViewById<Button>(
            R.id.btnLogout
        )


        // Edit Profile

        editProfileButton.setOnClickListener {

            val intent = Intent(
                requireContext(),
                EditProfileActivity::class.java
            )

            startActivity(intent)
        }


        // Logout

        logoutButton.setOnClickListener {

            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->

                    // Sign out from Firebase

                    FirebaseAuth.getInstance().signOut()


                    // Go to Login

                    val intent = Intent(
                        requireContext(),
                        LoginActivity::class.java
                    )

                    startActivity(intent)

                    requireActivity().finish()
                }
                .setNegativeButton("No", null)
                .show()
        }


        return view
    }


    // Refresh profile information

    override fun onResume() {
        super.onResume()

        // Get SharedPreferences

        val preferences = requireContext()
            .getSharedPreferences(
                "TaqreebData",
                Context.MODE_PRIVATE
            )


        // Get name from SharedPreferences

        val savedName = preferences.getString(
            "name",
            "User"
        )


        // Get email from Firebase

        val firebaseUser =
            FirebaseAuth.getInstance().currentUser

        val savedEmail =
            firebaseUser?.email ?: "No email"


        profileName.text = savedName
        profileEmail.text = savedEmail
    }
}

