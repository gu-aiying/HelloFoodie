package com.example.hellofoodie.presentation.recipeDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hellofoodie.R
import com.example.hellofoodie.databinding.ItemIngredientBinding
import com.example.hellofoodie.domain.model.ExtendedIngredient

class ExtendedIngredientsViewHolder(
    private val binding: ItemIngredientBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ingredient: ExtendedIngredient) {
        binding.apply {
            tvIngredientName.text = ingredient.name
            tvIngredientDetail.text = ingredient.original

            Glide.with(itemView)
                .load("https://spoonacular.com/cdn/ingredients_100x100/${ingredient.image}")
                .placeholder(R.drawable.ic_load)
                .error(R.drawable.ic_sad)
                .centerCrop()
                .into(ivIngredient)
        }
    }

    companion object {
        fun create(parent: ViewGroup): ExtendedIngredientsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ExtendedIngredientsViewHolder(
                binding = ItemIngredientBinding.inflate(inflater, parent, false)
            )
        }
    }
}