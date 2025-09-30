package com.example.hellofoodie.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellofoodie.domain.usecase.QueryAutoCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val autoCompleteUseCase: QueryAutoCompleteUseCase
) : ViewModel() {

    private val searchFlow = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val uiState: StateFlow<SearchUiState> = searchFlow
        .debounce(500L)
        .filter { it.length >= 3 }
        .flatMapLatest { query ->
            autoCompleteUseCase(query)
                .map { result ->
                    result.fold(
                        onSuccess = { SearchUiState.Success(it) },
                        onFailure = { SearchUiState.Error("Something went wrong: ${it.message}") }
                    )
                }
                .onStart { emit(SearchUiState.Loading) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchUiState.Initial
        )


    fun onQueryChanged(newQuery: String) {
        searchFlow.value = newQuery
    }

}