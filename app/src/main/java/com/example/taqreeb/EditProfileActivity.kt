package com.example.taqreeb

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_profile)

        val name = findViewById<EditText>(
            R.id.edtEditName
        )

        val email = findViewById<EditText>(
            R.id.edtEditEmail
        )

        val saveButton = findViewById<Button>(
            R.id.btnSaveProfile
        )

        val cancelButton = findViewById<Button>(
            R.id.btnCancelEdit
        )

        val preferences = getSharedPreferences(
            "TaqreebData",
            Context.MODE_PRIVATE
        )

        // Show current information
        name.setText(
            preferences.getString("name", "")
        )

        email.setText(
            preferences.getString("email", "")
        )

        // Save changes
        saveButton.setOnClickListener {

            val newName = name.text.toString()
            val newEmail = email.text.toString()

            if (newName.isEmpty() || newEmail.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                preferences.edit()
                    .putString("name", newName)
                    .putString("email", newEmail)
                    .apply()

                Toast.makeText(
                    this,
                    "Profile updated successfully!",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }

        // Cancel
        cancelButton.setOnClickListener {
            finish()
        }


    }
}