package com.example.hellofoodie.domain.model

data class Recipe (
    val id: Long,
    val image: String?,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val aggregateLikes: Int,
    val extendedIngredients: List<ExtendedIngredient>,
    val summary: String?,
    val dishTypes: List<String?>,
    val diets: List<String?>,
    val instructions: String?
)

