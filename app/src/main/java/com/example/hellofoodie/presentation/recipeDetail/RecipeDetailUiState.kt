package com.example.hellofoodie.presentation.recipeDetail

import com.example.hellofoodie.domain.model.Recipe

sealed class RecipeDetailUiState {
    object Initial: RecipeDetailUiState()
    object Loading: RecipeDetailUiState()
    object Empty: RecipeDetailUiState()
    data class Success(val recipeDetail: Recipe?): RecipeDetailUiState()
    data class Error(val message: String): RecipeDetailUiState()
}