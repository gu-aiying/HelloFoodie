package com.example.hellofoodie.presentation.popularRecipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hellofoodie.R
import com.example.hellofoodie.databinding.ItemRecipeBinding
import com.example.hellofoodie.domain.model.BasicRecipe

class RecipeViewHolder(
    private val binding: ItemRecipeBinding,
    private val onItemClick: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var currentRecipeId: Long = 0

    init {
        binding.root.setOnClickListener {
            onItemClick(currentRecipeId)
        }
    }

    fun bind(recipe: BasicRecipe) {
        binding.apply {

            currentRecipeId = recipe.id

            tvRecipeTitle.text = recipe.title

            Glide.with(itemView)
                .load(recipe.image)
                .placeholder(R.drawable.ic_load)
                .error(R.drawable.ic_sad_main)
                .centerCrop()
                .into(ivRecipe)

            tvLikes.text = itemView.resources.getQuantityString(
                R.plurals.recipe_likes_count,
                recipe.aggregateLikes,
                recipe.aggregateLikes
            )
            tvReadyMinutes.text = itemView.resources.getQuantityString(
                R.plurals.recipe_ready_minutes_count,
                recipe.readyInMinutes,
                recipe.readyInMinutes
            )
            tvServings.text = itemView.resources.getQuantityString(
                R.plurals.recipe_servings_count,
                recipe.servings,
                recipe.servings
            )

            if (recipe.diets.isEmpty()) {
                tvTags.visibility = View.GONE
            } else {
                // Если теги есть, показываем их
                tvTags.text = recipe.diets.joinToString("  |  ")
                tvTags.visibility = View.VISIBLE
            }
        }

    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClick: (Long) -> Unit
        ): RecipeViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return RecipeViewHolder(
                binding = ItemRecipeBinding.inflate(inflater, parent, false),
                onItemClick = onItemClick
            )
        }
    }
}