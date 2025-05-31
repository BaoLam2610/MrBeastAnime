package com.lambao.mrbeast.presentation.ui.fragment.anime_rating.detail

import android.os.Bundle
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.lambao.base.extension.click
import com.lambao.base.extension.getParcelableCompat
import com.lambao.base.extension.popBackStack
import com.lambao.base.presentation.ui.dialog.BaseVMDialog
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.presentation.ui.fragment.anime_rating.reviews.ReactionAdapter
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeReviewDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeReviewDetailFragment :
    BaseVMDialog<FragmentAnimeReviewDetailBinding, AnimeReviewDetailViewModel>() {

    private val argData by lazy {
        arguments?.getParcelableCompat<AnimeReviewDetailArgument>(Constants.Bundle.ARG)
    }

    private val reactionAdapter by lazy {
        ReactionAdapter()
    }

    override fun getLayoutResId() = R.layout.fragment_anime_review_detail

    override fun getTheme() = com.lambao.base.R.style.full_screen_dialog

    override fun getViewModelClass() = AnimeReviewDetailViewModel::class.java

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.rvReactions.adapter = reactionAdapter
        binding.rvReactions.setHasFixedSize(true)
        binding.rvReactions.spacing {
            top = 8
            bottom = 8
            start = 8
            end = 8
        }
        val layoutManager = FlexboxLayoutManager(binding.root.context)
        layoutManager.flexDirection = FlexDirection.ROW
        binding.rvReactions.setLayoutManager(layoutManager)
        binding.btnBack.click {
            popBackStack()
        }
    }

    override fun initObserve() {
        binding.viewModel = viewModel
        viewModel.setAnimeReview(argData?.review ?: return)

        reactionAdapter.submitList(
            argData?.review?.getReactionItems()?.filter { it.count > 0 }
                ?: emptyList()
        )
    }
}