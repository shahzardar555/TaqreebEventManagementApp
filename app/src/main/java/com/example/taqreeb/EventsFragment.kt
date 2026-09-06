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

        val noEvents = view.findViewById<TextView>(
            R.id.txtNoEvents
        )

        val eventsHeading = view.findViewById<TextView>(
            R.id.txtEventsHeading
        )


        // Category buttons

        val allEventsButton = view.findViewById<Button>(
            R.id.btnAllEvents
        )

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


        // Database

        val database = DatabaseHandler(
            requireContext()
        )


        // Function to display events

        fun displayEvents(events: ArrayList<Array<String>>) {

            eventsContainer.removeAllViews()

            if (events.isEmpty()) {

                noEvents.visibility = View.VISIBLE

            } else {

                noEvents.visibility = View.GONE

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

                    // Card appearance
                    eventCard.radius = 16f
                    eventCard.cardElevation = 5f
                    eventCard.setCardBackgroundColor(
                        android.graphics.Color.WHITE
                    )


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

                    // Make inside of card white
                    cardLayout.setBackgroundColor(
                        android.graphics.Color.WHITE
                    )


                    // Event name

                    val name = TextView(
                        requireContext()
                    )

                    name.text = event[0]
                    name.textSize = 20f
                    name.setTextColor(
                        android.graphics.Color.rgb(
                            34,
                            34,
                            34
                        )
                    )

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
                    date.setTextColor(
                        android.graphics.Color.rgb(
                            85,
                            85,
                            85
                        )
                    )

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
                    location.setTextColor(
                        android.graphics.Color.rgb(
                            85,
                            85,
                            85
                        )
                    )

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
                    category.setTextColor(
                        android.graphics.Color.rgb(
                            85,
                            85,
                            85
                        )
                    )

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
        }


        // Load all events

        val allEvents = database.getAllEvents()

        displayEvents(allEvents)


        // All Events button

        allEventsButton.setOnClickListener {

            eventsHeading.text = "All Events"

            displayEvents(
                database.getAllEvents()
            )
        }


        // University category

        universityButton.setOnClickListener {

            eventsHeading.text = "University Events"

            displayEvents(
                database.getEventsByCategory("University")
            )
        }


        // Sports category

        sportsButton.setOnClickListener {

            eventsHeading.text = "Sports Events"

            displayEvents(
                database.getEventsByCategory("Sports")
            )
        }


        // Business category

        businessButton.setOnClickListener {

            eventsHeading.text = "Business Events"

            displayEvents(
                database.getEventsByCategory("Business")
            )
        }


        // Concerts category

        concertsButton.setOnClickListener {

            eventsHeading.text = "Concerts"

            displayEvents(
                database.getEventsByCategory("Concerts")
            )
        }


        // Weddings category

        weddingsButton.setOnClickListener {

            eventsHeading.text = "Weddings"

            displayEvents(
                database.getEventsByCategory("Weddings")
            )
        }


        // Search Events

        searchText.setOnEditorActionListener { _, _, _ ->

            val text = searchText.text.toString().trim()

            if (text.isEmpty()) {

                eventsHeading.text = "All Events"

                displayEvents(
                    database.getAllEvents()
                )

            } else {

                val results =
                    database.searchAllEvents(text)

                if (results.isEmpty()) {

                    noEvents.visibility = View.VISIBLE

                    eventsContainer.removeAllViews()

                    Toast.makeText(
                        requireContext(),
                        "No event found",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    noEvents.visibility = View.GONE

                    eventsHeading.text = "Search Results"

                    displayEvents(results)
                }
            }

            true
        }


        return view
    }
}