package com.example.hellofoodie.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hellofoodie.data.local.dao.ExtendedIngredientDao
import com.example.hellofoodie.data.local.dao.RecipeDao
import com.example.hellofoodie.data.local.entity.ExtendedIngredientEntity
import com.example.hellofoodie.data.local.entity.RecipeEntity
import com.example.hellofoodie.data.local.typeconverter.RecipeTypeConverters

@Database(
    entities = [RecipeEntity::class, ExtendedIngredientEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(RecipeTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRecipeDao(): RecipeDao
    abstract fun getExtendedIngredientDao(): ExtendedIngredientDao
}