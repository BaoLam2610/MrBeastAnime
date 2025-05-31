package com.lambao.mrbeast.presentation.ui.fragment.anime_rating.reviews

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.lambao.base.extension.observeLatest
import com.lambao.base.extension.tryNavigate
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.base.presentation.ui.recycler_view.paging.DefaultLoadStateAdapter
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.data.model.anime.AnimeReview
import com.lambao.mrbeast.presentation.ui.fragment.anime_rating.detail.AnimeReviewDetailArgument
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeReviewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeReviewsFragment : BaseVMFragment<FragmentAnimeReviewsBinding, AnimeReviewsViewModel>() {

    companion object {
        fun newInstance(id: String): AnimeReviewsFragment {
            val args = Bundle().apply {
                putString(Constants.Bundle.ID, id)
            }

            val fragment = AnimeReviewsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val argId by lazy {
        arguments?.getString(Constants.Bundle.ID)
    }

    private val reviewAdapter by lazy {
        AnimeReviewsAdapter { item, _ ->
            tryNavigate(
                R.id.action_animeRatingFragment_to_animeReviewDetailFragment,
                args = bundleOf(
                    Constants.Bundle.ARG to AnimeReviewDetailArgument(item as AnimeReview)
                )
            )
        }
    }

    private val loadStateAdapter by lazy {
        DefaultLoadStateAdapter {
            reviewAdapter.retry()
        }
    }

    override fun getLayoutResId() = R.layout.fragment_anime_reviews

    override fun getViewModelClass() = AnimeReviewsViewModel::class.java

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.rvReviews.adapter = reviewAdapter.withLoadStateFooter(loadStateAdapter)
        binding.rvReviews.addItemDecoration(
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
        binding.rvReviews.spacing {
            top = 48
            bottom = 48
            start = 8
            end = 8
        }
    }

    override fun initObserve() {
        binding.viewModel = viewModel
        viewModel.setAnimeId(argId ?: "")

        observeLatest(viewModel.getAnimeReviewsPaginated()) {
            reviewAdapter.submitData(it)
        }

        reviewAdapter.addLoadStateListener { loadState ->
            viewModel.handleLoadStates(loadState, reviewAdapter.itemCount)
        }
    }
}