package com.mjob.moviecatalog.data.datasource.local.typeconverter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class PlatformConverter {
    @TypeConverter
    fun stringToPlatforms(data: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type

        return Gson().fromJson(data, type)
    }

    @TypeConverter
    fun platformsToString(platforms: List<String>): String {
        return Gson().toJson(platforms)
    }
}