package com.example.hellofoodie.presentation.popularRecipes

import androidx.recyclerview.widget.DiffUtil
import com.example.hellofoodie.domain.model.BasicRecipe

object RecipeDiffCallback : DiffUtil.ItemCallback<BasicRecipe>() {
    override fun areItemsTheSame(oldItem: BasicRecipe, newItem: BasicRecipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BasicRecipe, newItem: BasicRecipe): Boolean {
        return oldItem == newItem
    }
}
