package us.ttyl.musicsearch.dao

import com.squareup.moshi.Json

data class MusicItem(
    @field:Json(name = "artistName") val artistName: String,
    @field:Json(name = "trackId") val trackId: String,
    @field:Json(name = "trackName") val trackName: String,
    @field:Json(name = "collectionName") val collectionName: String,
    @field:Json(name = "artworkUrl100") val artworkUrl100: String,
    @field:Json(name = "previewUrl") val previewUrl: String,
    @field:Json(name = "tracktime") val tracktime: Long,
    @field:Json(name = "releaseDate") val releaseDate: String,
    @field:Json(name = "primaryGenreName") val primaryGenreName: String,
    @field:Json(name = "country") val country: String,
    @field:Json(name = "currency") val currency : String,
    @field:Json(name = "trackPrice") val trackPrice: String

)