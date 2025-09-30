package com.example.hellofoodie.domain.usecase

import com.example.hellofoodie.domain.model.Recipe
import com.example.hellofoodie.domain.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetRecipeDetailUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(recipeId: Long): Flow<Result<Recipe>> =
        flow {
            val recipe = recipeRepository.getRecipeDetail(recipeId)
            emit(Result.success(recipe))
        }.catch { e ->
            emit(Result.failure(e))
        }.flowOn(Dispatchers.IO)
}