package com.example.taqreeb

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class EventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_events,
            container,
            false
        )

        // Buttons and views
        val detailsButton = view.findViewById<Button>(
            R.id.btnEventDetails
        )

        val addEventButton = view.findViewById<Button>(
            R.id.btnAddEvent
        )

        val searchText = view.findViewById<EditText>(
            R.id.edtEventSearch
        )

        val eventsContainer = view.findViewById<LinearLayout>(
            R.id.eventsContainer
        )


        // Category buttons

        val universityButton = view.findViewById<Button>(
            R.id.btnUniversity
        )

        val sportsButton = view.findViewById<Button>(
            R.id.btnSports
        )

        val businessButton = view.findViewById<Button>(
            R.id.btnBusiness
        )

        val concertsButton = view.findViewById<Button>(
            R.id.btnConcerts
        )

        val weddingsButton = view.findViewById<Button>(
            R.id.btnWeddings
        )


        // Tech Innovation Summit details

        detailsButton.setOnClickListener {

            val intent = Intent(
                requireContext(),
                EventDetailsActivity::class.java
            )

            startActivity(intent)
        }


        // Create Event

        addEventButton.setOnClickListener {

            val intent = Intent(
                requireContext(),
                AddEventActivity::class.java
            )

            startActivity(intent)
        }


        // Create database object

        val database = DatabaseHandler(
            requireContext()
        )


        // Function to display events

        fun displayEvents(events: ArrayList<Array<String>>) {

            eventsContainer.removeAllViews()

            for (event in events) {

                val eventCard = CardView(
                    requireContext()
                )

                eventCard.layoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {

                        setMargins(
                            0,
                            12,
                            0,
                            12
                        )
                    }

                eventCard.radius = 16f
                eventCard.cardElevation = 5f


                val cardLayout = LinearLayout(
                    requireContext()
                )

                cardLayout.orientation =
                    LinearLayout.VERTICAL

                cardLayout.setPadding(
                    18,
                    18,
                    18,
                    18
                )


                // Event name

                val name = TextView(
                    requireContext()
                )

                name.text = event[0]
                name.textSize = 20f

                name.setTypeface(
                    null,
                    android.graphics.Typeface.BOLD
                )


                // Date

                val date = TextView(
                    requireContext()
                )

                date.text = "📅 ${event[1]}"
                date.textSize = 15f

                date.setPadding(
                    0,
                    10,
                    0,
                    0
                )


                // Location

                val location = TextView(
                    requireContext()
                )

                location.text = "📍 ${event[2]}"
                location.textSize = 15f

                location.setPadding(
                    0,
                    5,
                    0,
                    0
                )


                // Category

                val category = TextView(
                    requireContext()
                )

                category.text =
                    "Category: ${event[3]}"

                category.textSize = 15f

                category.setPadding(
                    0,
                    5,
                    0,
                    0
                )


                // Details button

                val details = Button(
                    requireContext()
                )

                details.text = "VIEW DETAILS"

                details.setOnClickListener {

                    val intent = Intent(
                        requireContext(),
                        EventDetailsActivity::class.java
                    )

                    intent.putExtra(
                        "searchEvent",
                        true
                    )

                    intent.putExtra(
                        "eventName",
                        event[0]
                    )

                    intent.putExtra(
                        "eventDate",
                        event[1]
                    )

                    intent.putExtra(
                        "eventLocation",
                        event[2]
                    )

                    intent.putExtra(
                        "eventCategory",
                        event[3]
                    )

                    intent.putExtra(
                        "eventDescription",
                        event[4]
                    )

                    startActivity(intent)
                }


                cardLayout.addView(name)
                cardLayout.addView(date)
                cardLayout.addView(location)
                cardLayout.addView(category)
                cardLayout.addView(details)

                eventCard.addView(cardLayout)

                eventsContainer.addView(eventCard)
            }
        }


        // Load all events

        val allEvents = database.getAllEvents()

        displayEvents(allEvents)


        // Search Events

        searchText.setOnEditorActionListener { _, _, _ ->

            val text = searchText.text.toString().trim()

            if (text.isEmpty()) {

                displayEvents(allEvents)

            } else {

                val results =
                    database.searchAllEvents(text)

                if (results.isEmpty()) {

                    Toast.makeText(
                        requireContext(),
                        "No event found",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    displayEvents(results)
                }
            }

            true
        }


        // University category

        universityButton.setOnClickListener {

            val results =
                database.getEventsByCategory("University")

            displayEvents(results)
        }


        // Sports category

        sportsButton.setOnClickListener {

            val results =
                database.getEventsByCategory("Sports")

            displayEvents(results)
        }


        // Business category

        businessButton.setOnClickListener {

            val results =
                database.getEventsByCategory("Business")

            displayEvents(results)
        }


        // Concerts category

        concertsButton.setOnClickListener {

            val results =
                database.getEventsByCategory("Concerts")

            displayEvents(results)
        }


        // Weddings category

        weddingsButton.setOnClickListener {

            val results =
                database.getEventsByCategory("Weddings")

            displayEvents(results)
        }


        return view
    }
}