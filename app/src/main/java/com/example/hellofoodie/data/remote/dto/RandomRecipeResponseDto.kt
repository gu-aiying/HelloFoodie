package com.example.hellofoodie.data.remote.dto

import com.example.hellofoodie.domain.model.Recipe
import kotlinx.serialization.Serializable

@Serializable
data class RandomRecipeResponseDto(
    val recipes: List<Recipe>
)

