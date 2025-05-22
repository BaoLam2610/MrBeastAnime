package com.lambao.mrbeast.data.repository.top

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.gson.Gson
import com.lambao.base.data.remote.BaseRemoteDataSource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.paging.TopAnimePagingSource
import com.lambao.mrbeast.data.remote.params.top.TopParams
import com.lambao.mrbeast.data.remote.service.TopService
import javax.inject.Inject

class TopRepositoryImpl @Inject constructor(
    private val topService: TopService,
    gson: Gson,
    dispatcherProvider: DispatcherProvider
) : BaseRemoteDataSource(gson, dispatcherProvider), TopRepository {
    override fun getTopAnime(params: TopParams) = safeApiCall {
        topService.getTopAnime(params.toQueryMap())
    }

    override fun getTopAnimePaginated(params: TopParams) = Pager(
        config = PagingConfig(
            pageSize = 20, // Số phần tử mỗi trang
            initialLoadSize = 20, // Số phần tử tải lần đầu, nên bằng pageSize
            prefetchDistance = 5, // Chỉ tải trang mới khi còn 5 item đến cuối danh sách
            enablePlaceholders = false, // Không sử dụng placeholder
        ),
        pagingSourceFactory = {
            TopAnimePagingSource(this, params)
        }
    ).flow
}