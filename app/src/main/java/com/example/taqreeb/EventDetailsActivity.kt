package com.example.taqreeb

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EventDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_event_details)

        val eventName = findViewById<TextView>(
            R.id.txtEventName
        )

        val eventDate = findViewById<TextView>(
            R.id.txtEventDate
        )

        val eventLocation = findViewById<TextView>(
            R.id.txtEventLocation
        )

        val eventCategory = findViewById<TextView>(
            R.id.txtEventCategory
        )

        val eventDescription = findViewById<TextView>(
            R.id.txtEventDescription
        )

        val registerButton = findViewById<Button>(
            R.id.btnRegister
        )

        val backButton = findViewById<Button>(
            R.id.btnBack
        )


        // Get SharedPreferences
        val preferences = getSharedPreferences(
            "TaqreebData",
            MODE_PRIVATE
        )


        // Check if this is the user-created event
        val isCreatedEvent = intent.getBooleanExtra(
            "createdEvent",
            false
        )


        // Check if an event was sent through Intent
        val hasSearchEvent = intent.getBooleanExtra(
            "searchEvent",
            false
        )


        if (hasSearchEvent) {

            // Get event information from Intent

            eventName.text = intent.getStringExtra(
                "eventName"
            )

            eventDate.text = intent.getStringExtra(
                "eventDate"
            )

            eventLocation.text = intent.getStringExtra(
                "eventLocation"
            )

            eventCategory.text = intent.getStringExtra(
                "eventCategory"
            )

            eventDescription.text = intent.getStringExtra(
                "eventDescription"
            )

        } else if (isCreatedEvent) {

            // Get user-created event from SharedPreferences

            eventName.text = preferences.getString(
                "createdEventName",
                ""
            )

            eventDate.text = preferences.getString(
                "createdEventDate",
                ""
            )

            eventLocation.text = preferences.getString(
                "createdEventLocation",
                ""
            )

            eventCategory.text = preferences.getString(
                "createdEventCategory",
                ""
            )

            eventDescription.text = preferences.getString(
                "createdEventDescription",
                ""
            )
        }


        // Register for event

        registerButton.setOnClickListener {

            preferences.edit()
                .putBoolean("registered", true)
                .putString(
                    "registeredEventName",
                    eventName.text.toString()
                )
                .putString(
                    "registeredEventDate",
                    eventDate.text.toString()
                )
                .putString(
                    "registeredEventLocation",
                    eventLocation.text.toString()
                )
                .apply()

            Toast.makeText(
                this,
                "Registration successful!",
                Toast.LENGTH_SHORT
            ).show()
        }


        // Back button

        backButton.setOnClickListener {
            finish()
        }
    }
}