package com.lambao.mrbeast.presentation.ui.fragment.base.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class SearchArgument(
    open val unApproved: Boolean?,
    open val q: String?,
    open val type: String?,
    open val score: Double?,
    open val minScore: Double?,
    open val maxScore: Double?,
    open val status: String?,
    open val rating: String?,
    open val sfw: Boolean?,
    open val genres: List<String>?,
    open val explicitGenres: List<String>?,
    open val orderBy: String?,
    open val sort: String?,
    open val letter: String?,
    open val producers: List<String>?,
    open val startDate: String?,
    open val endDate: String?
) : Parcelable