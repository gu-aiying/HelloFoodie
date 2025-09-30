package com.example.hellofoodie.di

import com.example.hellofoodie.data.local.dao.ExtendedIngredientDao
import com.example.hellofoodie.data.local.dao.RecipeDao
import com.example.hellofoodie.data.local.database.AppDatabase
import com.example.hellofoodie.data.local.mapper.IngredientMapper
import com.example.hellofoodie.data.local.mapper.RecipeMapper
import com.example.hellofoodie.data.remote.api.SpooncularApiService
import com.example.hellofoodie.data.repositoryImpl.RecipeRepositoryImpl
import com.example.hellofoodie.data.repositoryImpl.SearchRepositoryImpl
import com.example.hellofoodie.domain.repository.RecipeRepository
import com.example.hellofoodie.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeDao: RecipeDao,
        extendedIngredientDao: ExtendedIngredientDao,
        spooncularApiService: SpooncularApiService,
        appDatabase: AppDatabase,
        recipeMapper: RecipeMapper,
        ingredientMapper: IngredientMapper,
        networkMonitor: NetworkMonitor
    ): RecipeRepository {
        return RecipeRepositoryImpl(
            recipeDao = recipeDao,
            extendedIngredientDao = extendedIngredientDao,
            spooncularApiService = spooncularApiService,
            appDatabase = appDatabase,
            recipeMapper = recipeMapper,
            ingredientMapper = ingredientMapper,
            networkMonitor = networkMonitor
        )
    }

    @Provides
    @Singleton
    fun provideSearchRepository(
        networkMonitor: NetworkMonitor,
        recipeDao: RecipeDao,
        spooncularApiService: SpooncularApiService
    ): SearchRepository {
        return SearchRepositoryImpl(
            networkMonitor, recipeDao,
            spooncularApiService = spooncularApiService
        )
    }

}