package com.example.hellofoodie.domain.model

data class ExtendedIngredient(
    val id: Long,
    val image: String?,
    val name: String,
    val original: String, // amount + unit + originalName
    val originalName: String,
    val amount: Double,
    val unit: String?
)
