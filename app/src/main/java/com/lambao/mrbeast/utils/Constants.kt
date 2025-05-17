package com.lambao.mrbeast.utils

object Constants {
    const val URL_VER = "v4/"
    const val BASE_URL = "https://api.jikan.moe/$URL_VER"

    const val NETWORK_TIME_OUT = 3000L
    const val HTTP_CONTENT_TYPE_KEY = "Content-Type"
    const val HTTP_CONTENT_TYPE_VALUE = "application/json"
    const val AUTHORIZATION = "Authorization"

    const val PREF_FILE_NAME = "mrbeast_anime_pref"

    object Anime {
        object Type {
            const val TV = "TV"
            const val MOVIE = "Movie"
        }
    }

    object QueryParams {
        object Type {
            const val TV = "tv"
            const val MOVIE = "movie"
            const val OVA = "ova"
            const val SPECIAL = "special"
            const val ONA = "ona"
            const val MUSIC = "music"
            const val COMEDY = "cm"
            const val PERSONAL_VIDEO = "pv"
            const val TV_SPECIAL = "tv_special"
        }

        object Filter {
            const val AIRING = "airing"
            const val UPCOMING = "upcoming"
            const val BY_POPULARITY = "bypopularity"
            const val FAVORITE = "favorite"
            const val GENRES = "genres"
            const val EXPLICIT_GENRES = "explicit_genres"
            const val THEMES = "themes"
            const val DEMOGRAPHICS = "demographics"
        }

        /**
         * Ratings
         *
         * - G - All Ages
         * - PG - Children
         * - PG-13 - Teens 13 or older
         * - R - 17+ (violence & profanity)
         * - R+ - Mild Nudity
         * - Rx - Hentai
         *
         * */
        object Rating {
            const val G = "g"
            const val PG = "pg"
            const val PG13 = "pg13"
            const val R17 = "r17"
            const val R = "r"
            const val RX = "rx"
        }
    }

    object Bundle {
        const val ID = "id"
        const val STATE = "STATE"
        const val ARG = "ARG"
        const val IS_UPCOMING = "IS_UPCOMING"
        const val TITLE = "TITLE"
        const val TYPE = "TYPE"
        const val FILTER = "FILTER"
        const val RATING = "RATING"
        const val UNAPPROVED = "UNAPPROVED"
        const val CONTINUING = "CONTINUING"
        const val SFW = "SFW"
        const val SEASON_TYPE = "SEASON_TYPE"
    }

    object DateTime {
        const val yyyyMMddTHHmmssHHmm = "yyyy-MM-dd'T'HH:mm:ss+HH:mm"
        const val ddMMyyyy = "dd/MM/yyyy"
        const val ddMMyyyyHHmm = "dd/MM/yyyy HH:mm"
    }
}