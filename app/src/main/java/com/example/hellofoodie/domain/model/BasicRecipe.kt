package com.example.hellofoodie.domain.model

data class BasicRecipe(
    val id: Long,
    val image: String?,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val aggregateLikes: Int,
    val dishTypes: List<String?>,
    val diets: List<String?>
)
