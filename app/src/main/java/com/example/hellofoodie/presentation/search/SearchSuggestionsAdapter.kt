package com.example.hellofoodie.presentation.search

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.hellofoodie.domain.model.QueryAutoComplete


class SearchSuggestionsAdapter(
    private val onItemClick: (Long) -> Unit
) : ListAdapter<QueryAutoComplete, SearchSuggestionsViewHolder>(SuggestionDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchSuggestionsViewHolder {
        return SearchSuggestionsViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(
        holder: SearchSuggestionsViewHolder,
        position: Int
    ) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

}