package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.pictures

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.paging.ClientPagingViewModel
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.model.display.DisplayAnimePictureInfo
import com.lambao.mrbeast.domain.usecase.anime.GetAnimePicturesUseCase
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimePicturesViewModel @Inject constructor(
    private val getAnimePicturesUseCase: GetAnimePicturesUseCase,
    dispatcherProvider: DispatcherProvider
) : ClientPagingViewModel<DisplayAnimePictureInfo>(dispatcherProvider),
    AnimeDelegate by AnimeViewModel(dispatcherProvider) {

    fun fetchAnimePictures(id: String) {
        setAnimeId(id)
        fetchData()
    }

    override fun fetchData() {
        handleData(
            getAnimePicturesUseCase.invoke(AnimeParams(id = getAnimeId().value)),
            onSuccess = ::setFullItemList
        )
    }
}