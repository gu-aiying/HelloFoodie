package com.example.hellofoodie.presentation.popularRecipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hellofoodie.domain.model.BasicRecipe
import com.example.hellofoodie.domain.usecase.GetPopularRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PopularRecipesViewModel @Inject constructor(
    private val getPopularRecipesUseCase: GetPopularRecipesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PopularRecipesUiState>(PopularRecipesUiState.Initial)
    val uiState = _uiState.asStateFlow()

    val popularRecipes: Flow<PagingData<BasicRecipe>> =
        getPopularRecipesUseCase()
            .cachedIn(viewModelScope) // при поворачивании экрана данные сохранены; подписчики могут использовать кэш вместо повторной загрузки
}


