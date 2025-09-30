package com.example.hellofoodie.domain.usecase

import androidx.paging.PagingData
import com.example.hellofoodie.domain.model.BasicRecipe
import com.example.hellofoodie.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(): Flow<PagingData<BasicRecipe>> {
        return recipeRepository.getPopularRecipes()
    }
}