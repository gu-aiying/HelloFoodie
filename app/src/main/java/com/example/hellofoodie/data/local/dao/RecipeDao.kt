package com.example.hellofoodie.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.hellofoodie.data.local.entity.RecipeEntity

@Dao
interface RecipeDao {
    @Upsert
    suspend fun upsertAllRecipes(recipes: List<RecipeEntity>)

    @Query("SELECT * FROM recipes ORDER BY aggregateLikes DESC")
    fun getPopularRecipes(): PagingSource<Int, RecipeEntity>

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :query || '%' ORDER BY id")
    fun searchRecipes(query: String): PagingSource<Int, RecipeEntity>

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId: Long): RecipeEntity?

    //For test
    @Query("DELETE FROM recipes")
    suspend fun clearAllRecipes()
}