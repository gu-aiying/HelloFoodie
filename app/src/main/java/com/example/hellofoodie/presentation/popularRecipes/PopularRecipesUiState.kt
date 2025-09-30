package com.example.hellofoodie.presentation.popularRecipes

sealed class PopularRecipesUiState {
    object Initial: PopularRecipesUiState()
    object Loading: PopularRecipesUiState()
    object Success: PopularRecipesUiState()
    data class Error(val message: String): PopularRecipesUiState()
}