package com.lambao.mrbeast.presentation.ui.common.navigator

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.lambao.base.extension.navigate
import com.lambao.mrbeast.domain.model.display.DisplayAnimeInfo
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.AnimeDetailArgument
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R

class NavigatorDelegateImpl(
    val fragment: Fragment
) : NavigatorDelegate {
    override fun navigateHomeToDetail(item: DisplayAnimeInfo) {
        fragment.navigate(
            R.id.action_homeFragment_to_animeDetailFragment,
            bundleOf(
                Constants.Bundle.ARG to AnimeDetailArgument(
                    id = item.getId(),
                    sourceFragmentId = R.id.homeFragment
                )
            )
        )
    }

    override fun navigateTopAnimeToDetail(item: DisplayAnimeInfo) {
        fragment.navigate(
            R.id.action_topAnimeListFragment_to_animeDetailFragment,
            bundleOf(
                Constants.Bundle.ARG to AnimeDetailArgument(
                    id = item.getId(),
                    sourceFragmentId = R.id.topAnimeListFragment
                )
            )
        )
    }

    override fun navigateSeasonAnimeToDetail(item: DisplayAnimeInfo) {
        fragment.navigate(
            R.id.action_seasonAnimeListFragment_to_animeDetailFragment,
            bundleOf(
                Constants.Bundle.ARG to AnimeDetailArgument(
                    id = item.getId(),
                    sourceFragmentId = R.id.seasonAnimeListFragment
                )
            )
        )
    }

    override fun navigateGenreToDetail(item: DisplayAnimeInfo) {
        fragment.navigate(
            R.id.action_genresFragment_to_animeDetailFragment,
            bundleOf(
                Constants.Bundle.ARG to AnimeDetailArgument(
                    id = item.getId(),
                    sourceFragmentId = R.id.genresFragment
                )
            )
        )
    }

    override fun navigateSearchToDetail(item: DisplayAnimeInfo) {
        fragment.navigate(
            R.id.action_animeSearchFragment_to_animeDetailFragment,
            bundleOf(
                Constants.Bundle.ARG to AnimeDetailArgument(
                    id = item.getId(),
                    sourceFragmentId = R.id.animeSearchFragment
                )
            )
        )
    }
}