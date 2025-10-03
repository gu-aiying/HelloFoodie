package com.example.hellofoodie.data.remote.api

import com.example.hellofoodie.data.remote.dto.RandomRecipeResponseDto
import com.example.hellofoodie.data.remote.dto.RecipeSearchResultDto
import com.example.hellofoodie.domain.model.QueryAutoComplete
import com.example.hellofoodie.domain.model.Recipe
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpooncularApiService {

    @GET("/recipes/random")
    suspend fun getRandomRecipes(
        @Query("limitLicense") limitLicense: Boolean? = true, // Нужна ли лицензия для показа
        @Query("tags") tags: String? = null,  // Тэги
        @Query("number") number: Int? = 50, // Количество возвращаемых рецептов
    ): RandomRecipeResponseDto

    @GET("/recipes/autocomplete")
    suspend fun autoComplete(
        @Query("query") query: String,
        @Query("number") number: Int? = 10
    ): List<QueryAutoComplete>

    @GET("/recipes/{recipeId}/information")
    suspend fun getRecipeInformation(
        @Path("recipeId") recipeId: Long
    ): Recipe


    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("number") number: Int? = 10
    ): RecipeSearchResultDto
}