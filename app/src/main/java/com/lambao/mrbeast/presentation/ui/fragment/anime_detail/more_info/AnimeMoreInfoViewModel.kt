package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.more_info

import android.content.Context
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.model.anime.Anime
import com.lambao.mrbeast.domain.model.display.AnimeInfoPairTextAttr
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeCharactersUseCase
import com.lambao.mrbeast_anime.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AnimeMoreInfoViewModel @Inject constructor(
    private val getAnimeCharactersUseCase: GetAnimeCharactersUseCase,
    @ApplicationContext private val context: Context,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _anime = MutableStateFlow(Anime())
    val anime = _anime.asStateFlow()

    private val _moreInfos = _anime.map {
        buildList {
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
                        value = "${it.displayAiredFromDate()} - ${it.displayAiredToDate()}"
                    )
                )
            }

            if (it.episodes != null && it.episodes > 0) {
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

            if (it.shouldDisplayProducers()) {
                add(
                    AnimeInfoPairTextAttr(
                        key = context.getString(R.string.producers),
                        value = it.displayProducers()
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

            if (it.shouldDisplaySeasonYear()) {
                add(
                    AnimeInfoPairTextAttr(
                        key = context.getString(R.string.seasons),
                        value = it.displaySeasonYear()
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
        }
    }
    val moreInfos get() = _moreInfos

    fun setAnime(anime: Anime) {
        launch { _anime.emit(anime) }
    }
}