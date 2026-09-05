package com.example.taqreeb

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class MyEventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_my_events,
            container,
            false
        )

        val eventCard = view.findViewById<CardView>(
            R.id.eventCard
        )

        val noEvents = view.findViewById<TextView>(
            R.id.txtNoEvents
        )

        val cancelButton = view.findViewById<Button>(
            R.id.btnCancelEvent
        )

        val eventName = view.findViewById<TextView>(
            R.id.txtMyEventName
        )

        val eventDate = view.findViewById<TextView>(
            R.id.txtMyEventDate
        )

        val eventLocation = view.findViewById<TextView>(
            R.id.txtMyEventLocation
        )


        // Get SharedPreferences
        val preferences = requireContext().getSharedPreferences(
            "TaqreebData",
            Context.MODE_PRIVATE
        )

        // Check registration status
        val registered = preferences.getBoolean(
            "registered",
            false
        )


        if (registered) {

            eventCard.visibility = View.VISIBLE
            noEvents.visibility = View.GONE

            // Get the registered event information
            eventName.text = preferences.getString(
                "registeredEventName",
                "Event"
            )

            eventDate.text = preferences.getString(
                "registeredEventDate",
                "Date not available"
            )

            eventLocation.text = preferences.getString(
                "registeredEventLocation",
                "Location not available"
            )

        } else {

            eventCard.visibility = View.GONE
            noEvents.visibility = View.VISIBLE
        }


        // Cancel Registration
        cancelButton.setOnClickListener {

            AlertDialog.Builder(requireContext())
                .setTitle("Cancel Registration")
                .setMessage(
                    "Are you sure you want to cancel this event?"
                )
                .setPositiveButton("Yes") { _, _ ->

                    preferences.edit()
                        .putBoolean("registered", false)
                        .apply()

                    eventCard.visibility = View.GONE
                    noEvents.visibility = View.VISIBLE
                }
                .setNegativeButton("No", null)
                .show()
        }


        return view
    }
}