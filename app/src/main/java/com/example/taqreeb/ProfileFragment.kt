package com.example.taqreeb

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView
    private lateinit var profileImage: ImageView

    // Photo Picker
    private val photoPicker =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->

            if (uri != null) {

                profileImage.setImageURI(uri)

                // Save selected photo
                val preferences = requireContext()
                    .getSharedPreferences(
                        "TaqreebData",
                        Context.MODE_PRIVATE
                    )

                preferences.edit()
                    .putString(
                        "profileImageUri",
                        uri.toString()
                    )
                    .apply()
            }
        }


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

        profileImage = view.findViewById(
            R.id.imgProfile
        )

        val changePhotoButton = view.findViewById<Button>(
            R.id.btnChangePhoto
        )

        val editProfileButton = view.findViewById<Button>(
            R.id.btnEditProfile
        )

        val shareButton = view.findViewById<Button>(
            R.id.btnShareTaqreeb
        )

        val logoutButton = view.findViewById<Button>(
            R.id.btnLogout
        )


        // Change Photo

        changePhotoButton.setOnClickListener {

            photoPicker.launch("image/*")
        }


        // Edit Profile

        editProfileButton.setOnClickListener {

            val intent = Intent(
                requireContext(),
                EditProfileActivity::class.java
            )

            startActivity(intent)
        }


        // Share Taqreeb
        // This is an Implicit Intent

        shareButton.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_SEND
            )

            intent.type = "text/plain"

            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out Taqreeb.pk - Har Taqreeb, Ek Jagah!"
            )

            startActivity(
                Intent.createChooser(
                    intent,
                    "Share Taqreeb"
                )
            )
        }


        // Logout

        logoutButton.setOnClickListener {

            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->

                    FirebaseAuth.getInstance().signOut()

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


        // Load saved profile photo

        val savedImageUri = preferences.getString(
            "profileImageUri",
            null
        )

        if (savedImageUri != null) {

            profileImage.setImageURI(
                Uri.parse(savedImageUri)
            )
        }
    }
}