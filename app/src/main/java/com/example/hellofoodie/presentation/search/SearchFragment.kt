package com.example.hellofoodie.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hellofoodie.R
import com.example.hellofoodie.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding ?: throw Exception("FragmentSearchBinding === null")

    private val searchViewModel by viewModels<SearchViewModel>()

    private val suggestionsAdapter by lazy {
        SearchSuggestionsAdapter(
            onItemClick = { suggestionId ->
                // Перейти на страницу детализации рецепта
                val action = SearchFragmentDirections.actionSearchFragmentToRecipeDetailFragment(suggestionId)
                findNavController().navigate(action)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.uiState.collectLatest { state ->
                    observeUiState(state)
                }
            }
        }


        binding.apply {

            rvSuggestions.layoutManager = LinearLayoutManager(requireContext())
            rvSuggestions.adapter = suggestionsAdapter

            searchFragmentSearch.etSearch.doAfterTextChanged {
                searchViewModel.onQueryChanged(it.toString())
            }


        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeUiState(state: SearchUiState) {

        binding.apply {
            when (state) {
                is SearchUiState.Error -> {
                    tvNoInfo.setText(R.string.error_info)
                    tvNoInfo.isVisible = true
                    progressBar.isVisible = false
                    rvSuggestions.isVisible = false
                }

                SearchUiState.Initial -> {}
                SearchUiState.Loading -> {
                    tvNoInfo.isVisible = false
                    progressBar.isVisible = true
                    rvSuggestions.isVisible = false
                }

                is SearchUiState.Success -> {
                    tvNoInfo.isVisible = false
                    progressBar.isVisible = false
                    rvSuggestions.isVisible = true

                    suggestionsAdapter.submitList(state.suggestions)
                }
            }
        }
    }
}
