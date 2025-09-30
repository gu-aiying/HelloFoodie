package com.example.hellofoodie.data.local.mapper

import com.example.hellofoodie.data.local.entity.ExtendedIngredientEntity
import com.example.hellofoodie.data.local.entity.RecipeEntity
import com.example.hellofoodie.domain.model.BasicRecipe
import com.example.hellofoodie.domain.model.Recipe
import javax.inject.Inject

class RecipeMapper @Inject constructor(
    private val ingredientMapper: IngredientMapper
) {

    fun toRecipeEntity(recipe: Recipe): RecipeEntity {
        return RecipeEntity(
            id = recipe.id,
            title = recipe.title,
            image = recipe.image,
            readyInMinutes = recipe.readyInMinutes,
            servings = recipe.servings,
            aggregateLikes = recipe.aggregateLikes,
            summary = recipe.summary,
            dishTypes = recipe.dishTypes,
            diets = recipe.diets,
            instructions = recipe.instructions
        )
    }

    fun toRecipe(recipeEntity: RecipeEntity, ingredients: List<ExtendedIngredientEntity>): Recipe {
        return Recipe(
            id = recipeEntity.id,
            title = recipeEntity.title,
            image = recipeEntity.image,
            readyInMinutes = recipeEntity.readyInMinutes,
            servings = recipeEntity.servings,
            aggregateLikes = recipeEntity.aggregateLikes,
            extendedIngredients = ingredients.map { ingredientMapper.toExtendedIngredient(it) },
            summary = recipeEntity.summary,
            dishTypes = recipeEntity.dishTypes,
            diets = recipeEntity.diets,
            instructions = recipeEntity.instructions
        )
    }

    fun toBasicRecipe(recipeEntity: RecipeEntity): BasicRecipe {
        return BasicRecipe(
            id = recipeEntity.id,
            image = recipeEntity.image,
            title = recipeEntity.title,
            readyInMinutes = recipeEntity.readyInMinutes,
            servings = recipeEntity.servings,
            aggregateLikes = recipeEntity.aggregateLikes,
            dishTypes = recipeEntity.dishTypes,
            diets = recipeEntity.diets
        )
    }
}