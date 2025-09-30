package com.example.hellofoodie.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hellofoodie.databinding.ItemSuggestionBinding
import com.example.hellofoodie.domain.model.QueryAutoComplete

class SearchSuggestionsViewHolder(
    private val binding: ItemSuggestionBinding,
    private val onItemClick: (Long) -> Unit
)  : RecyclerView.ViewHolder(binding.root) {

    private var currentRecipeId: Long = 0

    init {
        binding.root.setOnClickListener {
            onItemClick(currentRecipeId)
        }
    }

    fun bind(suggestion: QueryAutoComplete) {
        binding.apply {
            currentRecipeId = suggestion.id

            tvSuggestion.text = suggestion.title
        }
    }

    companion object {
        fun create(parent: ViewGroup, onItemClick: (Long) -> Unit): SearchSuggestionsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return SearchSuggestionsViewHolder(
                binding = ItemSuggestionBinding.inflate(inflater, parent, false),
                onItemClick = onItemClick
            )
        }
    }
}