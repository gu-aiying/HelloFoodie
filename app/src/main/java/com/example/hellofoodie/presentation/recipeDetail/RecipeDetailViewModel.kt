package com.example.hellofoodie.presentation.recipeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellofoodie.domain.usecase.GetRecipeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase
) : ViewModel() {

    private val recipeIdFlow = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<RecipeDetailUiState> = recipeIdFlow
        .filterNotNull()
        .flatMapLatest { recipeId -> // когда id меняется, то автоматически начнется собрать рецепт
            getRecipeDetailUseCase(recipeId).map { result ->
                result.fold(
                    onSuccess = {
                        if (it == null) {
                            RecipeDetailUiState.Empty
                        } else {
                            RecipeDetailUiState.Success(it)
                        }
                    },
                    onFailure = { RecipeDetailUiState.Error("Something went wrong: ${it.message}") }
                )
            }
                .onStart { emit(RecipeDetailUiState.Loading) } // каждый раз когда начинается загрузка данных, то менять состояние экрана
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RecipeDetailUiState.Initial
        )

    fun setRecipeId(recipeId: Long) {
        recipeIdFlow.value = recipeId
    }
}