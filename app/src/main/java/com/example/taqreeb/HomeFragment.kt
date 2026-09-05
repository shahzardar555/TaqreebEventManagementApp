package com.example.taqreeb

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )

        val searchText = view.findViewById<EditText>(
            R.id.edtSearch
        )

        val searchButton = view.findViewById<Button>(
            R.id.btnSearch
        )

        val viewEventButton = view.findViewById<Button>(
            R.id.btnViewEvent
        )


        // View Tech Innovation Summit

        viewEventButton.setOnClickListener {

            val intent = Intent(
                requireContext(),
                EventDetailsActivity::class.java
            )

            startActivity(intent)
        }


        // Search Events

        searchButton.setOnClickListener {

            val text = searchText.text.toString().trim()

            if (text.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    "Please enter an event name or category",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val database = DatabaseHandler(
                    requireContext()
                )

                val event = database.searchEvents(text)


                if (event != null) {

                    // User-created event found

                    val intent = Intent(
                        requireContext(),
                        EventDetailsActivity::class.java
                    )

                    intent.putExtra("searchEvent", true)

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

                } else {

                    // Check built-in Tech Innovation Summit

                    if ("tech innovation summit".contains(
                            text.lowercase()
                        )
                    ) {

                        val intent = Intent(
                            requireContext(),
                            EventDetailsActivity::class.java
                        )

                        startActivity(intent)

                    } else {

                        Toast.makeText(
                            requireContext(),
                            "No event found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }


        return view
    }
}