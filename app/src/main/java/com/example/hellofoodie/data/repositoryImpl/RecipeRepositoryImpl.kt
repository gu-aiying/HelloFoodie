package com.example.hellofoodie.data.repositoryImpl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.hellofoodie.data.local.dao.ExtendedIngredientDao
import com.example.hellofoodie.data.local.dao.RecipeDao
import com.example.hellofoodie.data.local.database.AppDatabase
import com.example.hellofoodie.data.local.mapper.IngredientMapper
import com.example.hellofoodie.data.local.mapper.RecipeMapper
import com.example.hellofoodie.data.mediator.RecipeRemoteMediator
import com.example.hellofoodie.data.remote.api.SpooncularApiService
import com.example.hellofoodie.domain.model.BasicRecipe
import com.example.hellofoodie.domain.model.Recipe
import com.example.hellofoodie.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao,
    private val extendedIngredientDao: ExtendedIngredientDao,
    private val spooncularApiService: SpooncularApiService,
    private val appDatabase: AppDatabase,
    private val recipeMapper: RecipeMapper,
    private val ingredientMapper: IngredientMapper
) : RecipeRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPopularRecipes(): Flow<PagingData<BasicRecipe>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            remoteMediator = RecipeRemoteMediator(
                recipeDao = recipeDao,
                extendedIngredientDao = extendedIngredientDao,
                spooncularApiService = spooncularApiService,
                appDatabase = appDatabase,
                recipeMapper = recipeMapper,
                ingredientMapper = ingredientMapper
            ),
            pagingSourceFactory = { recipeDao.getPopularRecipes() }
        ).flow.map { pagingData ->
            pagingData.map { recipeEntity ->
                recipeMapper.toBasicRecipe(recipeEntity) // На главной странице нужно загружать только часть инфо по рецепту
            }
        }
    }

    override suspend fun getRecipeDetail(recipeId: Long): Recipe {
        val recipeEntity = recipeDao.getRecipeById(recipeId) ?: throw Exception("Recipe not found")
        val extendedIngredients = extendedIngredientDao.getIngredientsByRecipeId(recipeId)
        return recipeMapper.toRecipe(
            recipeEntity = recipeEntity,
            ingredients = extendedIngredients
        )
    }
}