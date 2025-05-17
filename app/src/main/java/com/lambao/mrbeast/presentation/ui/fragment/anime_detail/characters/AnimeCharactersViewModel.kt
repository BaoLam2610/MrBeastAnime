package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.characters

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.paging.ClientPagingViewModel
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.model.display.DisplayAnimeCharacterInfo
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeCharactersUseCase
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.empty_data.EmptyDataDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.empty_data.EmptyDataViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimeCharactersViewModel @Inject constructor(
    private val getAnimeCharactersUseCase: GetAnimeCharactersUseCase,
    dispatcherProvider: DispatcherProvider
) : ClientPagingViewModel<DisplayAnimeCharacterInfo>(dispatcherProvider),
    AnimeDelegate by AnimeViewModel(dispatcherProvider),
    EmptyDataDelegate by EmptyDataViewModel(dispatcherProvider) {

    override fun fetchData() {
        handleData(
            getAnimeCharactersUseCase.invoke(
                AnimeParams(id = getAnimeId().value)
            ),
            onError = {
                setShowEmptyData(true)
            }
        ) {
            setFullItemList(it)
            setShowEmptyData(it.isEmpty())
        }
    }

    fun fetchAnimeCharacters(id: String) {
        setAnimeId(id)
        resetPaging()
        fetchData()
    }
}