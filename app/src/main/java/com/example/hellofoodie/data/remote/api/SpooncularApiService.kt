package com.example.hellofoodie.data.remote.api

import com.example.hellofoodie.data.remote.dto.RandomRecipeResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SpooncularApiService {

    @GET("/recipes/random")
    suspend fun getRandomRecipes(
        @Query("limitLicense") limitLicense: Boolean? = true, // Нужна ли лицензия для показа
        @Query("tags") tags: String? = null,  // Тэги
        @Query("number") number: Int? = 50, // Количество возвращаемых рецептов
    ): RandomRecipeResponseDto
}