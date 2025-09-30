package com.example.hellofoodie.data.local.mapper

import com.example.hellofoodie.data.local.entity.ExtendedIngredientEntity
import com.example.hellofoodie.domain.model.ExtendedIngredient
import javax.inject.Inject

class IngredientMapper @Inject constructor() {

    fun toExtendedIngredientEntity(recipeId: Long, ingredient: ExtendedIngredient): ExtendedIngredientEntity {
        return ExtendedIngredientEntity(
            recipeId = recipeId,
            id = ingredient.id,
            image = ingredient.image,
            name = ingredient.name,
            original = ingredient.original,
            originalName = ingredient.originalName,
            amount = ingredient.amount,
            unit = ingredient.unit
        )
    }

    fun toExtendedIngredient(extendedIngredientEntity: ExtendedIngredientEntity): ExtendedIngredient {
        return ExtendedIngredient(
            id = extendedIngredientEntity.id,
            image = extendedIngredientEntity.image,
            name = extendedIngredientEntity.name,
            original = extendedIngredientEntity.original,
            originalName = extendedIngredientEntity.originalName,
            amount = extendedIngredientEntity.amount,
            unit = extendedIngredientEntity.unit
        )
    }
}