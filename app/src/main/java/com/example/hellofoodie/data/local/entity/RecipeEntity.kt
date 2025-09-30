package com.example.hellofoodie.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey
    val id: Long,

    val title: String,
    val image: String?,
    val readyInMinutes: Int,
    val servings: Int,
    val aggregateLikes: Int,
    val summary: String?,
    val dishTypes: List<String?>,
    val diets: List<String?>,
    val instructions: String?
)
