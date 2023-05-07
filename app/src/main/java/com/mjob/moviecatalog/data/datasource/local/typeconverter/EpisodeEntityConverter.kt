package com.mjob.moviecatalog.data.datasource.local.typeconverter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mjob.moviecatalog.data.datasource.local.model.EpisodeEntity

@ProvidedTypeConverter
class EpisodeEntityConverter {
    @TypeConverter
    fun stringToEpisodeEntity(data: String): List<EpisodeEntity> {
        val type = object : TypeToken<List<EpisodeEntity>>() {}.type

        return Gson().fromJson(data, type)
    }

    @TypeConverter
    fun episodeEntityToString(episodes: List<EpisodeEntity>): String {
        return Gson().toJson(episodes)
    }
}