package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.more_info

import android.content.Context
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.domain.model.display.AnimeInfoPairTextAttr
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeViewModel
import com.lambao.mrbeast_anime.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AnimeMoreInfoViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider),
    AnimeDelegate by AnimeViewModel(dispatcherProvider) {

    private val _moreInfos = getAnime().map {
        buildList {
            if (it == null) return@buildList
            if (it.shouldDisplayType()) {
                add(
                    AnimeInfoPairTextAttr(
                        key = context.getString(R.string.anime_type),
                        value = it.displayType()
                    )
                )
            }

            if (!it.status.isNullOrEmpty()) {
                add(
                    AnimeInfoPairTextAttr(
                        key = context.getString(R.string.status),
                        value = it.status
                    )
                )
            }

            if (it.shouldDisplayAired()) {
                add(
                    AnimeInfoPairTextAttr(
                        key = context.getString(R.string.broadcast),
                        value = it.displayAiredDate()
                    )
                )
            }

            if (it.episodes != null && it.episodes > 0 && !it.isMovieType()) {
                add(
                    AnimeInfoPairTextAttr(
                        key = context.getString(R.string.number_of_episodes),
                        value = it.episodes.toString()
                    )
                )
            }

            if (!it.duration.isNullOrEmpty()) {
                add(
                    AnimeInfoPairTextAttr(
                        key = context.getString(R.string.duration),
                        value = it.duration
                    )
                )
            }

            if (!it.rating.isNullOrEmpty()) {
                add(
                    AnimeInfoPairTextAttr(
                        key = context.getString(R.string.suitable_for),
                        value = it.rating
                    )
                )
            }

            if (it.shouldDisplayFavorites()) {
                add(
                    AnimeInfoPairTextAttr(
                        key = context.getString(R.string.favorites),
                        value = it.displayFavorites()
                    )
                )
            }

            if (it.shouldDisplaySeasonYear()) {
                add(
                    AnimeInfoPairTextAttr(
                        key = context.getString(R.string.seasons),
                        value = it.displaySeasonYear()
                    )
                )
            }

            if (it.shouldDisplayStudios()) {
                add(
                    AnimeInfoPairTextAttr(
                        key = context.getString(R.string.studios),
                        value = it.displayStudios()
                    )
                )
            }

            if (it.shouldDisplayProducers()) {
                add(
                    AnimeInfoPairTextAttr(
                        key = context.getString(R.string.producers),
                        value = it.displayProducers()
                    )
                )
            }
        }
    }
    val moreInfos get() = _moreInfos
}