package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.characters

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.paging.ClientPagingViewModel
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.model.display.DisplayAnimeCharacterInfo
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeCharactersUseCase
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimeCharactersViewModel @Inject constructor(
    private val getAnimeCharactersUseCase: GetAnimeCharactersUseCase,
    dispatcherProvider: DispatcherProvider
) : ClientPagingViewModel<DisplayAnimeCharacterInfo>(dispatcherProvider),
    AnimeDelegate by AnimeViewModel(dispatcherProvider) {

    override fun fetchData() {
        handleDataNoLoading(
            getAnimeCharactersUseCase.invoke(
                AnimeParams(id = getAnimeId().value)
            )
        ) {
            setFullItemList(it)
        }
    }

    fun fetchAnimeCharacters(id: String) {
        setAnimeId(id)
        resetPaging()
        fetchData()
    }
}