package com.example.hellofoodie.data.remote.dto

import com.example.hellofoodie.domain.model.Recipe

data class RandomRecipeResponseDto(
    val recipes: List<Recipe>
)

