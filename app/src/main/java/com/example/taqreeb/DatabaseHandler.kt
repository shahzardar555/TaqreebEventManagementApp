package com.example.taqreeb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, "Taqreeb.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
            "CREATE TABLE events (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "date TEXT, " +
                    "location TEXT, " +
                    "category TEXT, " +
                    "description TEXT)"
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        db.execSQL("DROP TABLE IF EXISTS events")

        onCreate(db)
    }

    fun addEvent(
        name: String,
        date: String,
        location: String,
        category: String,
        description: String
    ) {

        val db = writableDatabase

        val values = ContentValues()

        values.put("name", name)
        values.put("date", date)
        values.put("location", location)
        values.put("category", category)
        values.put("description", description)

        db.insert("events", null, values)

        db.close()
    }
    fun getEvent(): Array<String>? {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM events ORDER BY id DESC LIMIT 1",
            null
        )

        var event: Array<String>? = null

        if (cursor.moveToFirst()) {

            event = arrayOf(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
            )
        }

        cursor.close()
        db.close()

        return event
    }
    fun searchEvents(searchText: String): Array<String>? {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM events WHERE name LIKE ? OR category LIKE ? LIMIT 1",
            arrayOf(
                "%$searchText%",
                "%$searchText%"
            )
        )

        var event: Array<String>? = null

        if (cursor.moveToFirst()) {

            event = arrayOf(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
            )
        }

        cursor.close()
        db.close()

        return event
    }

    fun getAllEvents(): ArrayList<Array<String>> {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM events ORDER BY id DESC",
            null
        )

        val events = ArrayList<Array<String>>()

        if (cursor.moveToFirst()) {

            do {

                val event = arrayOf(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
                )

                events.add(event)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return events
    }

    fun searchAllEvents(searchText: String): ArrayList<Array<String>> {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM events WHERE name LIKE ? OR category LIKE ? ORDER BY id DESC",
            arrayOf(
                "%$searchText%",
                "%$searchText%"
            )
        )

        val events = ArrayList<Array<String>>()

        if (cursor.moveToFirst()) {

            do {

                val event = arrayOf(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
                )

                events.add(event)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return events
    }

    fun getEventsByCategory(
        category: String
    ): ArrayList<Array<String>> {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM events WHERE category LIKE ? ORDER BY id DESC",
            arrayOf("%$category%")
        )

        val events = ArrayList<Array<String>>()

        if (cursor.moveToFirst()) {

            do {

                val event = arrayOf(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
                )

                events.add(event)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return events
    }
}