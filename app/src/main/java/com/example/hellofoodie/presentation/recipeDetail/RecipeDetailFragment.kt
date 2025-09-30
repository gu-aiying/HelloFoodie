package com.example.hellofoodie.presentation.recipeDetail

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.example.hellofoodie.R
import com.example.hellofoodie.databinding.FragmentRecipeDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeDetailFragment : Fragment() {
    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding ?: throw Exception("FragmentRecipeDetailBinding === null")

    private val recipeDetailViewModel by viewModels<RecipeDetailViewModel>()

    private val extendedIngredientsAdapter by lazy {
        ExtendedIngredientsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeDetailBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: RecipeDetailFragmentArgs by navArgs()
        val recipeId = args.recipeId
        recipeDetailViewModel.setRecipeId(recipeId)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                recipeDetailViewModel.uiState.collect { state ->
                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                    observeUiState(state)
                }
            }
        }


        binding.apply {
            rvIngredients.layoutManager = LinearLayoutManager(requireContext())
            rvIngredients.adapter = extendedIngredientsAdapter

            // Заставить TextView реагировать на сенсорные события
            tvSummaryDetail.movementMethod = ScrollingMovementMethod()

            tvRecipeSummary.setOnClickListener {
                tvSummaryDetail.isVisible = !tvSummaryDetail.isVisible
            }
        }
    }

    private fun observeUiState(state: RecipeDetailUiState) {
        binding.apply {
            when (state) {
                RecipeDetailUiState.Initial -> {}
                RecipeDetailUiState.Loading -> {
                    progressBar.isVisible = true
                    contentGroup.isVisible = false
                    errorMessage.isVisible = false
                }

                is RecipeDetailUiState.Success -> {
                    progressBar.isVisible = false
                    contentGroup.isVisible = true
                    errorMessage.isVisible = false
                    // Обновить данные
                    val detail = state.recipeDetail
                    tvRecipeBasicInfo.tvRecipeTitle.text = detail.title
                    tvRecipeBasicInfo.tvTags

                    val context = requireContext()
                    Glide.with(context)
                        .load(detail.image)
                        .placeholder(R.drawable.ic_load)
                        .error(R.drawable.ic_sad)
                        .centerCrop()
                        .into(tvRecipeBasicInfo.ivRecipe)

                    tvRecipeBasicInfo.tvLikes.text = context.resources.getQuantityString(
                        R.plurals.recipe_likes_count,
                        detail.aggregateLikes,
                        detail.aggregateLikes
                    )
                    tvRecipeBasicInfo.tvReadyMinutes.text = context.resources.getQuantityString(
                        R.plurals.recipe_ready_minutes_count,
                        detail.readyInMinutes,
                        detail.readyInMinutes
                    )
                    tvRecipeBasicInfo.tvServings.text = context.resources.getQuantityString(
                        R.plurals.recipe_servings_count,
                        detail.servings,
                        detail.servings
                    )

                    if (detail.diets.isEmpty()) {
                        tvRecipeBasicInfo.tvTags.visibility = View.GONE
                    } else {
                        // Если теги есть, показываем их
                        tvRecipeBasicInfo.tvTags.text = detail.diets.joinToString("  |  ")
                        tvRecipeBasicInfo.tvTags.visibility = View.VISIBLE
                    }

                    detail.summary?.let {
                        tvSummaryDetail.text = htmlToString(it)
                    }

                    extendedIngredientsAdapter.submitList(detail.extendedIngredients)
                }

                is RecipeDetailUiState.Error -> {
                    progressBar.isVisible = false
                    contentGroup.isVisible = false
                    errorMessage.isVisible = true
                }
            }
        }

    }

    private fun htmlToString(text: String): Spanned {
//        val removalMarker = "<a href=\"https:"
//        val cleanedText = text.substringBefore(removalMarker)
        val formattedText: Spanned = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        return formattedText
    }
}