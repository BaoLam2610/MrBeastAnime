package com.lambao.mrbeast.data.remote.params.search

import com.lambao.mrbeast.data.remote.params.PagingRequest

data class SearchParams(
    val unApproved: Boolean? = null, // This is a flag. When supplied it will include entries which are unapproved. Unapproved entries on MyAnimeList are those that are user submitted and have not yet been approved by MAL to show up on other pages. They will have their own specifc pages and are often removed resulting in a 404 error. You do not need to pass a value to it. e.g usage: `?unapproved`
    val q: String = "",
    val type: String = "",
    val score: Double? = null,
    val minScore: Double? = null, // Set a minimum score for results.
    val maxScore: Double? = null, // Set a maximum score for results.
    val status: String = "",
    val rating: String = "",
    val sfw: Boolean? = null, // Filter out Adult entries
    val genres: List<String> = emptyList(), // Filter by genre(s) IDs. Can pass multiple with a comma as a delimiter. e.g 1,2,3
    val genresExclude: List<String> = emptyList(), // Exclude genre(s) IDs. Can pass multiple with a comma as a delimiter. e.g 1,2,3
    val orderBy: String = "",
    val sort: String = "",
    val letter: String = "", // Return entries starting with the given letter
    val producers: List<String> = emptyList(), // Filter by producer(s) IDs. Can pass multiple with a comma as a delimiter. e.g 1,2,3
    val startDate: String = "", // Filter by starting date. Format: YYYY-MM-DD. e.g `2022`, `2005-05`, `2005-01-01`
    val endDate: String = "", // Filter by ending date. Format: YYYY-MM-DD. e.g `2022`, `2005-05`, `2005-01-01`
    override val page: Int = 1,
    override val limit: Int = 20
) : PagingRequest(page, limit) {
    override fun toQueryMap(): Map<String, String> {
        return super.toQueryMap() + mapOf(
            "unapproved" to (unApproved?.toString() ?: ""),
            "q" to q,
            "type" to type,
            "score" to (score?.toString() ?: ""),
            "min_score" to (minScore?.toString() ?: ""),
            "max_score" to (maxScore?.toString() ?: ""),
            "status" to status,
            "rating" to rating,
            "sfw" to (sfw?.toString() ?: ""),
            "genres" to genres.joinToString(","),
            "genres_exclude" to genresExclude.joinToString(","),
            "order_by" to orderBy,
            "sort" to sort,
            "letter" to letter,
            "producers" to producers.joinToString(","),
            "start_date" to startDate,
            "end_date" to endDate
        )
    }
}