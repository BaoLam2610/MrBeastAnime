package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.recommendations

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.lambao.presentation.extension.getParcelableCompat
import com.lambao.presentation.extension.observeLatest
import com.lambao.presentation.extension.tryNavigate
import com.lambao.base.presentation.ui.fragment.paging.BaseManualPagingFragment
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.AnimeDetailArgument
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeRecommendationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeRecommendationsFragment :
    BaseManualPagingFragment<FragmentAnimeRecommendationsBinding, AnimeRecommendationsViewModel>() {

    companion object {
        fun newInstance(data: AnimeRecommendationsArgument?): AnimeRecommendationsFragment {
            val args = Bundle().apply {
                putParcelable(Constants.Bundle.ARG, data)
            }

            val fragment = AnimeRecommendationsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val argData by lazy {
        arguments?.getParcelableCompat<AnimeRecommendationsArgument>(Constants.Bundle.ARG)
    }

    private val recommendationsAdapter by lazy {
        AnimeRecommendationsAdapter { item, _ ->
            tryNavigate(
                R.id.animeDetailFragment,
                args = bundleOf(
                    Constants.Bundle.ARG to AnimeDetailArgument(
                        id = item.getId(),
                        sourceFragmentId = argData?.sourceFragmentId ?: R.id.homeFragment
                    )
                ),
                navOptions = {
                    setPopUpTo(
                        argData?.sourceFragmentId ?: R.id.homeFragment,
                        inclusive = false
                    )
                }
            )
        }
    }

    override fun getLayoutResId() = R.layout.fragment_anime_recommendations

    override fun getViewModelClass() = AnimeRecommendationsViewModel::class.java

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.rvRecommendations.adapter = recommendationsAdapter
        binding.rvRecommendations.addItemDecoration(
            MaterialDividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            ).apply {
                dividerThickness = 1
                dividerColor = ContextCompat.getColor(
                    requireContext(),
                    com.lambao.base.R.color.background_disabled
                )
            }
        )
    }

    override fun initObserve() {
        binding.viewModel = viewModel

        observeLatest(viewModel.items) {
            recommendationsAdapter.submitList(it)
        }

        argData?.let { state ->
            viewModel.fetchAnimeRecommendations(state.id ?: "")
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}