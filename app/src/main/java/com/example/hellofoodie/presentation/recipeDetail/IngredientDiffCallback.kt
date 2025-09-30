package com.example.hellofoodie.presentation.recipeDetail

import androidx.recyclerview.widget.DiffUtil
import com.example.hellofoodie.domain.model.ExtendedIngredient

object IngredientDiffCallback : DiffUtil.ItemCallback<ExtendedIngredient>() {
    override fun areItemsTheSame(
        oldItem: ExtendedIngredient,
        newItem: ExtendedIngredient
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ExtendedIngredient,
        newItem: ExtendedIngredient
    ): Boolean {
        return oldItem == newItem
    }

}