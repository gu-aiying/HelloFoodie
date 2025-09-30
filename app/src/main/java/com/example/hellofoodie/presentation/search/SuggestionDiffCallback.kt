package com.example.hellofoodie.presentation.search

import androidx.recyclerview.widget.DiffUtil
import com.example.hellofoodie.domain.model.QueryAutoComplete

object SuggestionDiffCallback : DiffUtil.ItemCallback<QueryAutoComplete>() {
    override fun areItemsTheSame(oldItem: QueryAutoComplete, newItem: QueryAutoComplete): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: QueryAutoComplete,
        newItem: QueryAutoComplete
    ): Boolean {
        return oldItem == newItem
    }
}