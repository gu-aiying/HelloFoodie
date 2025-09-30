package com.example.hellofoodie.presentation.popularRecipes

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.hellofoodie.domain.model.BasicRecipe

class PopularRecipesAdapter (
    private val onItemClick: (Long) -> Unit,
) : PagingDataAdapter<BasicRecipe, RecipeViewHolder>(RecipeDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeViewHolder {
        return RecipeViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(
        holder: RecipeViewHolder,
        position: Int
    ) {
        getItem(position)?.let { recipe ->
            holder.bind(recipe)
        }
    }
}