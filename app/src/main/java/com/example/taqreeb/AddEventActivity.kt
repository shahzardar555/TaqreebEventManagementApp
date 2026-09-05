package com.example.taqreeb

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_event)

        val eventName = findViewById<EditText>(
            R.id.edtEventName
        )

        val eventDate = findViewById<EditText>(
            R.id.edtEventDate
        )

        val eventLocation = findViewById<EditText>(
            R.id.edtEventLocation
        )

        val eventCategory = findViewById<EditText>(
            R.id.edtEventCategory
        )

        val eventDescription = findViewById<EditText>(
            R.id.edtEventDescription
        )

        val createButton = findViewById<Button>(
            R.id.btnCreateEvent
        )

        createButton.setOnClickListener {

            val name = eventName.text.toString()
            val date = eventDate.text.toString()
            val location = eventLocation.text.toString()
            val category = eventCategory.text.toString()
            val description = eventDescription.text.toString()

            if (name.isEmpty() ||
                date.isEmpty() ||
                location.isEmpty() ||
                category.isEmpty() ||
                description.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                // Create database object
                val database = DatabaseHandler(this)

                // Save event into SQLite
                database.addEvent(
                    name,
                    date,
                    location,
                    category,
                    description
                )

                // Keep SharedPreferences for the current app
                val preferences = getSharedPreferences(
                    "TaqreebData",
                    Context.MODE_PRIVATE
                )

                preferences.edit()
                    .putString("createdEventName", name)
                    .putString("createdEventDate", date)
                    .putString("createdEventLocation", location)
                    .putString("createdEventCategory", category)
                    .putString("createdEventDescription", description)
                    .putBoolean("eventCreated", true)
                    .apply()

                Toast.makeText(
                    this,
                    "Event saved successfully!",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }
    }
}