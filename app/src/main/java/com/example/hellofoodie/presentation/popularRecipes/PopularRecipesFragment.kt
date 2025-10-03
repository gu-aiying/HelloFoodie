package com.example.hellofoodie.presentation.popularRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.hellofoodie.databinding.FragmentPopularRecipesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularRecipesFragment : Fragment() {

    private var _binding: FragmentPopularRecipesBinding? = null

    private val binding
        get() = _binding ?: throw Exception("FragmentPopularRecipesBinding === null")

    private val popularRecipesViewModel by viewModels<PopularRecipesViewModel>()

    private val popularRecipesAdapter by lazy {
        PopularRecipesAdapter(onItemClick = { recipeId ->
            // перейти на страницу детализацией
            val action = PopularRecipesFragmentDirections.actionPopularRecipesFragmentToRecipeDetailFragment(recipeId)
            findNavController().navigate(action)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        observeUiState()

        binding.apply {
            rvPopularRecipes.layoutManager = LinearLayoutManager(requireContext())
            rvPopularRecipes.adapter = popularRecipesAdapter

            // Сбор LoadStateFlow (поток состояний списка)
            observeLoadState()

            // Следить за изменением данных
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    popularRecipesViewModel.popularRecipes.collectLatest { pagingData ->
                        popularRecipesAdapter.submitData(pagingData)
                    }
                }
            }

            swipeRefreshLayout.setOnRefreshListener {
                // Использовать adapter.refresh() для запуска RemoteMediator
                popularRecipesAdapter.refresh()
            }

            // Запрещать фокус у searchBar
            popularRecipesSearchBar.etSearch.apply {
                isFocusable = false
                isClickable = true
            }

            // Перейти на экран поиска
            popularRecipesSearchBar.etSearch.setOnClickListener {
                val action = PopularRecipesFragmentDirections.actionPopularRecipesFragmentToSearchFragment()
                findNavController().navigate(action)
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeLoadState() {
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                popularRecipesAdapter.loadStateFlow.collectLatest { loadStates ->

                    val refreshState = loadStates.mediator?.refresh

                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())

                    if (refreshState is LoadState.Loading) {
                        // progressBar.isVisible = true
                        rvPopularRecipes.isVisible = false
                    } else { // успешно или с ошибкой
                        swipeRefreshLayout.isRefreshing = false
                        progressBar.isVisible = false
                        rvPopularRecipes.isVisible = true
                    }

                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                    // Управление состоянием "Нет данных"
                    tvNoInfo.isVisible =
                        loadStates.refresh is LoadState.NotLoading && popularRecipesAdapter.itemCount == 0

                    // Управление сообщением об ошибке
                    if (refreshState is LoadState.Error) {
                        when (refreshState.error) {
                            is java.io.IOException -> Toast.makeText(
                                requireContext(),
                                "Нет соединения с Интернетом.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }


//    private fun observeUiState() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                popularRecipesViewModel.uiState.collect { uiState ->
//                    when (uiState) {
//                        is PopularRecipesUiState.Initial,
//                        is PopularRecipesUiState.Loading -> {
//                        }
//
//                        is PopularRecipesUiState.Success -> {}
//                        is PopularRecipesUiState.Error -> {}
//                    }
//                }
//            }
//        }
//    }

}