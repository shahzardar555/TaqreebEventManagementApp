package com.example.taqreeb

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

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

        val eventCategory = findViewById<Spinner>(
            R.id.edtEventCategory
        )

        val eventDescription = findViewById<EditText>(
            R.id.edtEventDescription
        )

        val createButton = findViewById<Button>(
            R.id.btnCreateEvent
        )


        // Date Picker

        eventDate.setOnClickListener {

            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->

                    val selectedDate =
                        "$selectedDay/${selectedMonth + 1}/$selectedYear"

                    eventDate.setText(selectedDate)
                },
                year,
                month,
                day
            )

            datePicker.show()
        }


        // Event Categories

        val categories = arrayOf(
            "Select Category",
            "University",
            "Business",
            "Sports",
            "Concerts",
            "Weddings",
            "Cultural"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )

        eventCategory.adapter = adapter


        // Create Event

        createButton.setOnClickListener {

            val name = eventName.text.toString()
            val date = eventDate.text.toString()
            val location = eventLocation.text.toString()
            val category = eventCategory.selectedItem.toString()
            val description = eventDescription.text.toString()


            // Check empty fields

            if (name.isEmpty() ||
                date.isEmpty() ||
                location.isEmpty() ||
                description.isEmpty() ||
                category == "Select Category"
            ) {

                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                // Save event in SQLite

                val database = DatabaseHandler(this)

                database.addEvent(
                    name,
                    date,
                    location,
                    category,
                    description
                )


                // Save event information in SharedPreferences

                val preferences = getSharedPreferences(
                    "TaqreebData",
                    Context.MODE_PRIVATE
                )

                preferences.edit()
                    .putString(
                        "createdEventName",
                        name
                    )
                    .putString(
                        "createdEventDate",
                        date
                    )
                    .putString(
                        "createdEventLocation",
                        location
                    )
                    .putString(
                        "createdEventCategory",
                        category
                    )
                    .putString(
                        "createdEventDescription",
                        description
                    )
                    .putBoolean(
                        "eventCreated",
                        true
                    )
                    .apply()


                Toast.makeText(
                    this,
                    "Event saved successfully!",
                    Toast.LENGTH_SHORT
                ).show()


                // Close Create Event screen

                finish()
            }
        }
    }
}