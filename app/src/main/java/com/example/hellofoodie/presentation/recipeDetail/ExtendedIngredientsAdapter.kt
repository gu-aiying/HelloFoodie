package com.example.hellofoodie.presentation.recipeDetail

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.hellofoodie.domain.model.ExtendedIngredient

class ExtendedIngredientsAdapter () :
    ListAdapter<ExtendedIngredient, ExtendedIngredientsViewHolder>(IngredientDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExtendedIngredientsViewHolder {
        return ExtendedIngredientsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ExtendedIngredientsViewHolder, position: Int) {
        getItem(position)?.let { ingredient ->
            holder.bind(ingredient)
        }
    }
}