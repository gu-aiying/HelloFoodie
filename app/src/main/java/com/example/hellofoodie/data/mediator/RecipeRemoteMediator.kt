package com.example.hellofoodie.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.hellofoodie.data.local.dao.ExtendedIngredientDao
import com.example.hellofoodie.data.local.dao.RecipeDao
import com.example.hellofoodie.data.local.database.AppDatabase
import com.example.hellofoodie.data.local.entity.RecipeEntity
import com.example.hellofoodie.data.local.mapper.IngredientMapper
import com.example.hellofoodie.data.local.mapper.RecipeMapper
import com.example.hellofoodie.data.remote.api.SpooncularApiService
import com.example.hellofoodie.domain.model.Recipe
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RecipeRemoteMediator @Inject constructor (
    private val recipeDao: RecipeDao,
    private val extendedIngredientDao: ExtendedIngredientDao,
    private val spooncularApiService: SpooncularApiService,
    private val appDatabase: AppDatabase,
    private val recipeMapper: RecipeMapper,
    private val ingredientMapper: IngredientMapper
): RemoteMediator<Int, RecipeEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RecipeEntity>
    ): MediatorResult {
       return try {
           val page = when (loadType) {
               // Свайп наверх
               LoadType.PREPEND -> {
                   // C помощью endOfPaginationReached = true запретить загрузку новых данных при свайпе вверх
                   return MediatorResult.Success(endOfPaginationReached = true)
               }
               // REFRESH и APPEND проходят дальше для выполнения сетевого запроса
               LoadType.REFRESH, LoadType.APPEND -> Unit
           }
            // Загружать данные по API
            val response = spooncularApiService.getRandomRecipes(number = 20)
            val recipes = response.recipes

            // Управление БД
            appDatabase.withTransaction { // для обеспечения атомарности и целостности данных

                saveRandomRecipes(recipes)
            }

            MediatorResult.Success(endOfPaginationReached = false)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun saveRandomRecipes(recipes: List<Recipe>) {
        val recipeEntities = recipes.map { recipeMapper.toRecipeEntity(it) }
        recipeDao.upsertAllRecipes(recipeEntities)

        val ingredientEntities = recipes.flatMap { recipe ->
            recipe.extendedIngredients.map { ingredient ->
                ingredientMapper.toExtendedIngredientEntity(recipe.id, ingredient)
            }
        }
        extendedIngredientDao.upsertAllIngredients(ingredientEntities)
    }
}