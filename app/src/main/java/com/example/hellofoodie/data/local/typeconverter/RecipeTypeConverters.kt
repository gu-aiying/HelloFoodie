package com.example.hellofoodie.data.local.typeconverter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

@ProvidedTypeConverter
class RecipeTypeConverters @Inject constructor(
    private val gson: Gson // Inject the Gson instance
) {
    // Converter for List<String>
    @TypeConverter
    fun fromListString(value: List<String?>): String {
        return gson.toJson(value) // Use Gson to convert List to JSON string
    }

    @TypeConverter
    fun toListString(value: String): List<String?> {
        // Use TypeToken to help Gson deserialize back into a List<String>
        val type = object : TypeToken<List<String?>>() {}.type
        return gson.fromJson(value, type)
    }
}