package com.example.hellofoodie.di

import android.content.Context
import androidx.room.Room
import com.example.hellofoodie.data.local.dao.ExtendedIngredientDao
import com.example.hellofoodie.data.local.dao.RecipeDao
import com.example.hellofoodie.data.local.database.AppDatabase
import com.example.hellofoodie.data.local.typeconverter.RecipeTypeConverters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    fun provideRecipeTypeConverters(gson: Gson): RecipeTypeConverters {
        return RecipeTypeConverters(gson)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        converter: RecipeTypeConverters
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "hello_foodie_database"
        )
            .addTypeConverter(converter)
            .fallbackToDestructiveMigration(true) // Временно, пока не написаны миграции
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeDao(database: AppDatabase): RecipeDao {
        return database.getRecipeDao()
    }

    @Provides
    @Singleton
    fun provideExtendedIngredientDao(database: AppDatabase): ExtendedIngredientDao {
        return database.getExtendedIngredientDao()
    }

}