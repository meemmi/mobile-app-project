package com.example.pawtracker.data.local.converters

import androidx.room.TypeConverter
import com.example.pawtracker.model.LocationPoint
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * TypeConverter for Room database.
 * Converts a List<LocationPoint> into a JSON String and back.
 * Room does not support storing complex types (like lists of custom objects)
 * directly in the database, so we serialize the list using Gson.
 */

class LocationPointConverter {

    private val gson = Gson()

    //Converts a list of LocationPoint objects into a JSON string so it can be stored in the Room database.
    @TypeConverter
    fun fromList(list: List<LocationPoint>): String {
        return gson.toJson(list)
    }

    //Converts a JSON string from the database back into a List<LocationPoint> object.
    @TypeConverter
    fun toList(json: String): List<LocationPoint> {
        val type = object : TypeToken<List<LocationPoint>>() {}.type
        return gson.fromJson(json, type)
    }
}