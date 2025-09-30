package com.example.hellofoodie.presentation.search

import com.example.hellofoodie.domain.model.QueryAutoComplete

sealed class SearchUiState {
    object Initial: SearchUiState()
    object Loading: SearchUiState()
    data class Success(val suggestions: List<QueryAutoComplete>): SearchUiState()
    data class Error(val message: String): SearchUiState()
}