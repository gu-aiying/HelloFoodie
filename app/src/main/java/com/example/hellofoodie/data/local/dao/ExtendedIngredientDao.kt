package com.example.hellofoodie.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.hellofoodie.data.local.entity.ExtendedIngredientEntity

@Dao
interface ExtendedIngredientDao {

    @Upsert
    suspend fun upsertAllIngredients(ingredients: List<ExtendedIngredientEntity>)

    @Query("SELECT * FROM extended_ingredients WHERE recipeId = :recipeId")
    suspend fun getIngredientsByRecipeId(recipeId: Long): List<ExtendedIngredientEntity>

    // For test
    @Query("DELETE FROM extended_ingredients")
    suspend fun clearAllIngredients()
}