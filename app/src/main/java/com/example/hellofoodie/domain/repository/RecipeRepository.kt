package com.example.hellofoodie.domain.repository

import androidx.paging.PagingData
import com.example.hellofoodie.domain.model.BasicRecipe
import com.example.hellofoodie.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getPopularRecipes(): Flow<PagingData<BasicRecipe>>

    suspend fun getRecipeDetail(recipeId: Long): Recipe
}