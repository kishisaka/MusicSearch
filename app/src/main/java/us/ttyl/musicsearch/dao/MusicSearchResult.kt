package us.ttyl.musicsearch.dao

import com.squareup.moshi.Json

data class MusicSearchResult(@field:Json(name = "resultCount") val resultCount: Int,
                             @field:Json(name = "results") val results: List<MusicItem>)